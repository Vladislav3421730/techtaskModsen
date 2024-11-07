package com.example.libraryapi.exceptions;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @ApiResponse(
            responseCode = "500",
            description = "Ошибка на стороне сервера",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AppError.class)
            )
    )
    @ExceptionHandler(Exception.class)
    public ResponseEntity<AppError> handleException(Exception exception){
        return new ResponseEntity<>(new AppError(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
