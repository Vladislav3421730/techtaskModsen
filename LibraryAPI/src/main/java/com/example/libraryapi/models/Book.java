package com.example.libraryapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_id_seq")
    @SequenceGenerator(name = "book_id_seq", sequenceName = "book_id_seq", allocationSize = 1)
    @EqualsAndHashCode.Exclude
    private Long id;

    private String ISBN;
    private String name;
    private String genre;
    private String description;
    private String author;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "book")
    @JsonIgnore
    private BookStatus bookStatus;

    public void AddBookStatus(BookStatus bookStatus){
        this.setBookStatus(bookStatus);
        bookStatus.setBook(this);
    }

    public Book(Long id, String ISBN, String name, String genre, String description, String author) {
        this.id = id;
        this.ISBN = ISBN;
        this.name = name;
        this.genre = genre;
        this.description = description;
        this.author = author;
    }
}
