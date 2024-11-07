package com.example.libraryapi.controllers;


import com.example.libraryapi.dto.BookDto;
import com.example.libraryapi.dto.BookStatusDto;
import com.example.libraryapi.exceptions.AppError;
import com.example.libraryapi.services.LibraryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/library")
@Tag(name = "Эндпоинты для управления библиотекой", description = "Применяются для работы с книгами и статусами книг")
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
public class LibraryController {

    private final LibraryService libraryService;

    @ApiResponse(
            responseCode = "200",
            description = "Свободные книги получены успешно",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = BookDto.class))))
    @Operation(summary = "Получение всех свободных книг", description = "Возвращает список всех книг, доступных для аренды")
    @GetMapping(value = "/get/free", produces = "application/json")
    public List<BookDto> findAllFreeBook(){
        return libraryService.findAvailableBooks();
    }

    @ApiResponse(
            responseCode = "200",
            description = "Статусы книг успешно получены",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = BookStatusDto.class))))
    @Operation(summary = "Получение всех статусов книг", description = "Возвращает список всех статусов книг в библиотеке")
    @GetMapping(value = "/get",produces = "application/json")
    public List<BookStatusDto> findAllStatus(){
        return libraryService.findAll();
    }

    @ApiResponse(
            responseCode = "200",
            description = "Статуса книги успешно получен по id",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = BookStatusDto.class))))
    @Operation(summary = "Получение статуса книги по ID", description = "Возвращает статус книги на основе её ID")
    @GetMapping(value = "/get/{book_status_id}", produces = "application/json" )
    public BookStatusDto findStatusById(@PathVariable Long book_status_id){
        return libraryService.findBookStatusById(book_status_id);
    }

    @ApiResponse(
            responseCode = "200",
            description = "Статуса книги успешно обновлён",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = BookStatusDto.class))))
    @Operation(summary = "Обновление статуса книги", description = "Обновляет статус книги на основе данных, переданных в теле запроса")
    @PutMapping(value = "/update",consumes ="application/json",produces = "application/json")
    public BookStatusDto updateBookStatus(@RequestBody BookStatusDto bookStatusDto){
        return libraryService.updateBookStatus(bookStatusDto);
    }



}
