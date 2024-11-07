package com.example.libraryapi.services.Impl;


import com.example.libraryapi.dto.BookDto;
import com.example.libraryapi.dto.BookStatusDto;
import com.example.libraryapi.exceptions.BookStatusNotFoundException;
import com.example.libraryapi.models.BookStatus;
import com.example.libraryapi.repositories.BookRepository;
import com.example.libraryapi.repositories.BookStatusRepository;
import com.example.libraryapi.services.LibraryService;
import jakarta.transaction.Transactional;
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
    public List<BookDto> findAvailableBooks() {
        return bookRepository.findAllFree().stream()
                .map(book -> modelMapper.map(book, BookDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookStatusDto> findAll() {
       return bookStatusRepository.findAll().stream()
               .map(bookStatus -> modelMapper.map(bookStatus, BookStatusDto.class))
               .collect(Collectors.toList());
    }

    @Override
    public BookStatusDto findBookStatusById(Long id) {
        BookStatus bookStatus=bookStatusRepository.findById(id)
                .orElseThrow(()->new BookStatusNotFoundException(String.format("Status with id %d wasn't found",id)));
        return modelMapper.map(bookStatus, BookStatusDto.class);
    }

    @Override
    @Transactional
    public BookStatusDto updateBookStatus(BookStatusDto bookStatusDto) {
        BookStatus existingBookStatus=bookStatusRepository.findById(bookStatusDto.getId())
                .orElseThrow(()->new BookStatusNotFoundException(String.format("Status with id %d wasn't found. Updating is impossible",bookStatusDto.getId())));
        modelMapper.map(bookStatusDto, existingBookStatus);
        return modelMapper.map(bookStatusRepository.save(existingBookStatus),BookStatusDto.class);
    }
}
