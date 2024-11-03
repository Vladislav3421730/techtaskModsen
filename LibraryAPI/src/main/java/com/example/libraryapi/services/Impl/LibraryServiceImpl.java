package com.example.libraryapi.services.Impl;


import com.example.libraryapi.dto.BookDto;
import com.example.libraryapi.dto.BookStatusDto;
import com.example.libraryapi.repositories.BookRepository;
import com.example.libraryapi.repositories.BookStatusRepository;
import com.example.libraryapi.services.LibraryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private final BookRepository bookRepository;
    private final BookStatusRepository bookStatusRepository;
    private final ModelMapper modelMapper;


    @Override
    public List<BookDto> getAvailableBooks() {
        return bookRepository.findAllFree().stream()
                .map(book -> modelMapper.map(book, BookDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void updateBookStatus(BookStatusDto bookStatusDto) {

    }
}
