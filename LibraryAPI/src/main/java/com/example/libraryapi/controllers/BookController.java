package com.example.libraryapi.controllers;

import com.example.libraryapi.dto.BookDto;
import com.example.libraryapi.dto.ResponseDto;
import com.example.libraryapi.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    @GetMapping("/all")
    public List<BookDto> findAllBooks(){
        return bookService.findAll();
    }

    @GetMapping("/{book_id}")
    public BookDto findBookById(@PathVariable Long book_id ) {
        return bookService.findById(book_id);
    }

    @GetMapping("/{isbn}")
    public BookDto findBookByISBN(@PathVariable String isbn) {
        return bookService.findByIsbn(isbn);
    }

    @PostMapping("/save")
    public BookDto saveBook(@RequestBody BookDto bookDto){
        return bookService.save(bookDto);
    }

    @PutMapping("/update")
    public BookDto updateBook(@RequestBody BookDto bookDto){
        return bookService.update(bookDto);
    }

    @DeleteMapping("/{book_id}")
    public ResponseEntity<ResponseDto> deleteBook(@PathVariable Long book_id){
      bookService.delete(book_id);
        return ResponseEntity.ok(new ResponseDto(String.format("Book with id %d has been deleted",book_id)));
    }
}
