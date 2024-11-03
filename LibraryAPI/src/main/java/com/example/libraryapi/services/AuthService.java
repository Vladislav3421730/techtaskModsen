package com.example.libraryapi.services;

import com.example.libraryapi.dto.JwtResponseDto;
import com.example.libraryapi.dto.LoginUserDto;
import com.example.libraryapi.dto.RegisterUserDto;
import com.example.libraryapi.dto.ResponseDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<JwtResponseDto> createAuthToken(LoginUserDto userDto);
    ResponseEntity<ResponseDto> registerUser(RegisterUserDto userDto);
}
