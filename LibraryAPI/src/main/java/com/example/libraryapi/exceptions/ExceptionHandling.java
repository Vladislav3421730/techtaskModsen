package com.example.libraryapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<AppError> handleLoginFailedException(LoginFailedException loginFailedException){
        return new ResponseEntity<>(new AppError(loginFailedException.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RegistrationFailedException.class)
    public ResponseEntity<AppError> handleRegistrationException(RegistrationFailedException registrationFailedException){
        return new ResponseEntity<>(new AppError(registrationFailedException.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<AppError> handleBookNotFoundException(BookNotFoundException bookNotFoundException){
        return new ResponseEntity<>(new AppError(bookNotFoundException.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookStatusNotFoundException.class)
    public ResponseEntity<AppError> handleBookStatusNotFoundException(BookStatusNotFoundException bookStatusNotFoundException){
        return new ResponseEntity<>(new AppError(bookStatusNotFoundException.getMessage()), HttpStatus.NOT_FOUND);
    }

}
