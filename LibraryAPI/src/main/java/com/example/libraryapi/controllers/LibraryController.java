package com.example.libraryapi.controllers;


import com.example.libraryapi.dto.BookDto;
import com.example.libraryapi.services.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/library")
public class LibraryController {

    private final LibraryService libraryService;

    @GetMapping("/free")
    public List<BookDto> findAllFreeBook(){
        return libraryService.getAvailableBooks();
    }

}
