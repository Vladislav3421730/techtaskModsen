package com.example.libraryapi.dto;


import lombok.Data;

@Data
public class BookDto {

    private Long id;
    private String ISBN;
    private String name;
    private String genre;
    private String description;
    private String author;

}
