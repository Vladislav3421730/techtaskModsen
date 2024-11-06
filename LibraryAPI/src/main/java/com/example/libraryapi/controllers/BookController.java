package com.example.libraryapi.controllers;

import com.example.libraryapi.dto.BookDto;
import com.example.libraryapi.dto.ResponseDto;
import com.example.libraryapi.services.BookService;
import com.example.libraryapi.services.Impl.BookServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;


    @GetMapping(value = "/get",produces = "application/json")
    public List<BookDto> findAllBooks(){
        return bookService.findAll();
    }

    @GetMapping(value = "/get/{book_id}",produces = "application/json")
    public BookDto findBookById(@PathVariable Long book_id ) {
        return bookService.findById(book_id);
    }

    @GetMapping(value = "/get/isbn/{isbn}", produces = "application/json")
    public BookDto findBookByISBN(@PathVariable String isbn) {
        return bookService.findByIsbn(isbn);
    }

    @PostMapping(value = "/save",consumes = "application/json",produces = "application/json")
    public BookDto saveBook(@RequestBody BookDto bookDto){
        return bookService.save(bookDto);
    }

    @PutMapping(value = "/update",consumes = "application/json",produces = "application/json")
    public BookDto updateBook(@RequestBody BookDto bookDto){
        return bookService.update(bookDto);
    }

    @DeleteMapping(value = "/delete/{book_id}",produces = "application/json")
    public ResponseEntity<ResponseDto> deleteBook(@PathVariable Long book_id){
      bookService.delete(book_id);
      return ResponseEntity.ok(new ResponseDto(String.format("Book with id %d has been deleted",book_id)));
    }
}
