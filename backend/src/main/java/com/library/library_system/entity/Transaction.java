package com.library.library_system.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime issueDate;
    private LocalDateTime dueDate;
    private LocalDateTime returnDate;
    
    private String borrowReason;
    private String returnCondition;
    
    // New fields for purchase/rent flow
    private String phone;
    private String email;
    private String transactionType; // "RENT" or "PURCHASE"
    private Double amount;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        ACTIVE, RETURNED, OVERDUE
    }
}
