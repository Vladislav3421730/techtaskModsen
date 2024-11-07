package com.example.libraryapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Сущность книги")
public class BookDto {

    @Schema(description = "Уникальный идентификатор книги", example = "1")
    private Long id;

    @Schema(description = "ISBN книги", example = "978-3-16-148410-0")
    private String isbn;

    @Schema(description = "Название книги", example = "The Great Gatsby")
    private String name;

    @Schema(description = "Жанр книги", example = "Fiction")
    private String genre;

    @Schema(description = "Краткое описание книги", example = "A novel about the American dream.")
    private String description;

    @Schema(description = "Автор книги", example = "F. Scott Fitzgerald")
    private String author;
}
