package com.library.library_system.controller;

import com.library.library_system.entity.Book;
import com.library.library_system.entity.Transaction;
import com.library.library_system.entity.User;
import com.library.library_system.repository.BookRepository;
import com.library.library_system.repository.TransactionRepository;
import com.library.library_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @GetMapping("/user/{userId}")
    public List<Transaction> getUserTransactions(@PathVariable Long userId) {
        return transactionRepository.findByUserId(userId);
    }

    @PostMapping("/issue")
    public ResponseEntity<?> issueBook(
            @RequestParam Long bookId, 
            @RequestParam Long userId, 
            @RequestParam(required = false) String borrowReason,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String transactionType,
            @RequestParam(required = false) Double amount) {
        Optional<Book> bookOpt = bookRepository.findById(bookId);
        Optional<User> userOpt = userRepository.findById(userId);

        if (bookOpt.isPresent() && userOpt.isPresent()) {
            Book book = bookOpt.get();
            if (book.getAvailableCopies() > 0) {
                book.setAvailableCopies(book.getAvailableCopies() - 1);
                bookRepository.save(book);

                Transaction t = new Transaction();
                t.setBook(book);
                t.setUser(userOpt.get());
                t.setIssueDate(LocalDateTime.now());
                t.setDueDate(LocalDateTime.now().plusDays(14));
                t.setStatus(Transaction.Status.ACTIVE);
                t.setBorrowReason(borrowReason);
                t.setPhone(phone);
                t.setEmail(email);
                t.setTransactionType(transactionType);
                t.setAmount(amount);
                
                return ResponseEntity.ok(transactionRepository.save(t));
            } else {
                return ResponseEntity.badRequest().body("Book is out of stock");
            }
        }
        return ResponseEntity.badRequest().body("Invalid book or user");
    }

    @PostMapping("/return/{transactionId}")
    public ResponseEntity<?> returnBook(@PathVariable Long transactionId, @RequestParam(required = false) String returnCondition) {
        Optional<Transaction> tOpt = transactionRepository.findById(transactionId);
        if (tOpt.isPresent()) {
            Transaction t = tOpt.get();
            t.setStatus(Transaction.Status.RETURNED);
            t.setReturnDate(LocalDateTime.now());
            t.setReturnCondition(returnCondition);
            transactionRepository.save(t);

            Book book = t.getBook();
            book.setAvailableCopies(book.getAvailableCopies() + 1);
            bookRepository.save(book);

            return ResponseEntity.ok(t);
        }
        return ResponseEntity.badRequest().body("Transaction not found");
    }
}
