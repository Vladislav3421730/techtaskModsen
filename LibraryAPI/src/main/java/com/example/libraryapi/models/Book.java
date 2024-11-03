package com.example.libraryapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "book")
    @JsonIgnore
    private BookStatus bookStatus;

    public void AddBookStatus(BookStatus bookStatus){
        this.setBookStatus(bookStatus);
        bookStatus.setBook(this);
    }

}
