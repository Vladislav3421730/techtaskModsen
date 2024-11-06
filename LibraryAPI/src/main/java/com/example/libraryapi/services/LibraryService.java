package com.example.libraryapi.services;

import com.example.libraryapi.dto.BookDto;
import com.example.libraryapi.dto.BookStatusDto;
import com.example.libraryapi.models.Book;

import java.util.List;

public interface LibraryService {

    List<BookDto> findAvailableBooks();
    List<BookStatusDto> findAll();
    BookStatusDto findBookStatusById(Long id);
    BookStatusDto updateBookStatus(BookStatusDto bookStatusDto);

}
