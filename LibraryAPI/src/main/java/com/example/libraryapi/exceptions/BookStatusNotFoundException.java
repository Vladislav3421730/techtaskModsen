package com.example.libraryapi.exceptions;

public class BookStatusNotFoundException extends RuntimeException {
    public BookStatusNotFoundException(String message) {
        super(message);
    }
}
