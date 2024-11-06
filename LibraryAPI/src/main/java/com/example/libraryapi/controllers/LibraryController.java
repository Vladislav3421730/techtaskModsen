package com.example.libraryapi.controllers;


import com.example.libraryapi.dto.BookDto;
import com.example.libraryapi.dto.BookStatusDto;
import com.example.libraryapi.services.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/library")
public class LibraryController {

    private final LibraryService libraryService;

    @GetMapping(value = "/get/free", produces = "application/json")
    public List<BookDto> findAllFreeBook(){
        return libraryService.findAvailableBooks();
    }

    @GetMapping(value = "/get",produces = "application/json")
    public List<BookStatusDto> findAllStatus(){
        return libraryService.findAll();
    }

    @GetMapping(value = "/get/{book_status_id}", produces = "application/json" )
    public BookStatusDto findStatusById(@PathVariable Long book_status_id){
        return libraryService.findBookStatusById(book_status_id);
    }

    @PutMapping(value = "/update",consumes ="application/json",produces = "application/json")
    public BookStatusDto updateBookStatus(@RequestBody BookStatusDto bookStatusDto){
        return libraryService.updateBookStatus(bookStatusDto);
    }



}
