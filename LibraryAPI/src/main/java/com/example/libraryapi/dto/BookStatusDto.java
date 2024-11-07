package com.example.libraryapi.dto;

import com.example.libraryapi.models.Book;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Сущность статуса книги")
public class BookStatusDto {

    @Schema(description = "Уникальный идентификатор записи статуса книги", example = "1")
    private Long id;

    @Schema(description = "Дата и время, когда книга была взята", example = "2024-11-07 10:30:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime borrowedAt;

    @Schema(description = "Дата и время, до которого книга должна быть возвращена", example = "2024-11-14 10:30:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dueDate;

    @Schema(description = "Информация о книге, к которой относится данный статус")
    private Book book;
}
