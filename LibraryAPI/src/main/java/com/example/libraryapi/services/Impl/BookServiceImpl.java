package com.example.libraryapi.services.Impl;

import com.example.libraryapi.dto.BookDto;
import com.example.libraryapi.exceptions.BookNotFoundException;
import com.example.libraryapi.models.Book;
import com.example.libraryapi.models.BookStatus;
import com.example.libraryapi.repositories.BookRepository;
import com.example.libraryapi.services.BookService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Override
    public BookDto save(BookDto bookDto) {
        Book book=modelMapper.map(bookDto, Book.class);
        book.AddBookStatus(new BookStatus());
        bookRepository.save(book);
        return bookDto;
    }

    @Override
    public BookDto update(BookDto bookDto) {
        Book book=bookRepository.findById(bookDto.getId())
                .orElseThrow(()-> new BookNotFoundException(String.format("Book with id %d wasn't find",bookDto.getId())));
        modelMapper.map(bookDto, book);
        bookRepository.save(book);
        return modelMapper.map(book, BookDto.class);
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(book -> modelMapper.map(book, BookDto.class))
                .collect(Collectors.toList());
    }


    @Override
    public BookDto findByIsbn(String isbn) {
        Book book=bookRepository.findByISBN(isbn)
                .orElseThrow(()-> new BookNotFoundException(String.format("Book with ISBN %s wasn't find",isbn)));
        return modelMapper.map(book, BookDto.class);
    }

    @Override
    public BookDto findById(Long id) {
        Book book=bookRepository.findById(id)
                .orElseThrow(()-> new BookNotFoundException(String.format("Book with id %d wasn't find",id)));
        return modelMapper.map(book, BookDto.class);
    }
}
