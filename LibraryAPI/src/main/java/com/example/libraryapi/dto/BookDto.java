package com.example.libraryapi.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class BookDto {

    private Long id;
    private String isbn;
    private String name;
    private String genre;
    private String description;
    private String author;

}
