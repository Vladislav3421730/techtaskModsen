package com.example.libraryapi.controllers;

import com.example.libraryapi.dto.BookDto;
import com.example.libraryapi.dto.ResponseDto;
import com.example.libraryapi.exceptions.AppError;
import com.example.libraryapi.services.BookService;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
@Tag(name = "Эндпоинты для работы с книгами", description = "Позволяет выполнять операции по добавлению, обновлению, удалению и поиску книг")
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "401",
                description = "Пользователь не был авторизован",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = AppError.class)
                )
        ),
        @ApiResponse(
                responseCode = "403",
                description = "Операция запрещена",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = AppError.class)
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Книга не найдена",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = AppError.class)
                )
        )
})
public class BookController {

    private final BookService bookService;

    @ApiResponse(
            responseCode = "200",
            description = "Книги получены успешно",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = BookDto.class))))
    @Operation(summary = "Получение всех книг", description = "Возвращает список всех книг, доступных в системе")
    @GetMapping(value = "/get", produces = "application/json")
    public List<BookDto> findAllBooks() {
        return bookService.findAll();
    }

    @ApiResponse(
            responseCode = "200",
            description = "Книга успешно получена по её id",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = BookDto.class)))
    @Operation(summary = "Поиск книги по ID", description = "Возвращает книгу по её уникальному идентификатору")
    @GetMapping(value = "/get/{book_id}", produces = "application/json")
    public BookDto findBookById(@PathVariable Long book_id) {
        return bookService.findById(book_id);
    }

    @ApiResponse(
            responseCode = "200",
            description = "Книга успешно получена по её isbn",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = BookDto.class)))
    @Operation(summary = "Поиск книги по ISBN", description = "Возвращает книгу по её уникальному номеру ISBN")
    @GetMapping(value = "/get/isbn/{isbn}", produces = "application/json")
    public BookDto findBookByISBN(@PathVariable String isbn) {
        return bookService.findByIsbn(isbn);
    }

    @ApiResponse(
            responseCode = "200",
            description = "Книга успешно сохранена",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = BookDto.class)))
    @Operation(summary = "Сохранение новой книги", description = "Позволяет добавить новую книгу в систему")
    @PostMapping(value = "/save", consumes = "application/json", produces = "application/json")
    public BookDto saveBook(@RequestBody BookDto bookDto) {
        return bookService.save(bookDto);
    }

    @ApiResponse(
            responseCode = "200",
            description = "Книга успешно обновлена",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = BookDto.class)))
    @Operation(summary = "Обновление существующей книги", description = "Позволяет обновить информацию о существующей книге")
    @PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public BookDto updateBook(@RequestBody BookDto bookDto) {
        return bookService.update(bookDto);
    }

    @ApiResponse(
            responseCode = "200",
            description = "Книга успешно удалена",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseDto.class)))
    @Operation(summary = "Удаление книги по ID", description = "Удаляет книгу по её уникальному идентификатору")
    @DeleteMapping(value = "/delete/{book_id}", produces = "application/json")
    public ResponseEntity<ResponseDto> deleteBook(@PathVariable Long book_id) {
        bookService.delete(book_id);
        return ResponseEntity.ok(new ResponseDto(String.format("Book with id %d has been deleted", book_id)));
    }
}
