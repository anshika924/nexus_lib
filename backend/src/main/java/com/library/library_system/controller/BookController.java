package com.library.library_system.controller;

import com.library.library_system.entity.Book;
import com.library.library_system.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public List<Book> getAllBooks(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category) {
        
        List<Book> books = bookRepository.findAll();
        
        if (category != null && !category.trim().isEmpty() && !category.equals("All Categories")) {
            books = books.stream().filter(b -> category.equals(b.getCategory())).toList();
        }
        
        if (search != null && !search.trim().isEmpty()) {
            String s = search.toLowerCase();
            books = books.stream().filter(b -> 
                b.getTitle().toLowerCase().contains(s) || 
                b.getAuthor().toLowerCase().contains(s)
            ).toList();
        }
        
        return books;
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        book.setAvailableCopies(book.getTotalCopies());
        return ResponseEntity.ok(bookRepository.save(book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
