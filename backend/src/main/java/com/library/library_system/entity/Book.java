package com.library.library_system.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "books")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    private String category;

    @Column(unique = true, nullable = false)
    private String isbn;

    private String coverUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    private int totalCopies;
    private int availableCopies;
}
