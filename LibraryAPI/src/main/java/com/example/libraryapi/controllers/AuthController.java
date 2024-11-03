package com.example.libraryapi.controllers;

import com.example.libraryapi.dto.JwtResponseDto;
import com.example.libraryapi.dto.LoginUserDto;
import com.example.libraryapi.dto.RegisterUserDto;
import com.example.libraryapi.dto.ResponseDto;
import com.example.libraryapi.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> createToken(@RequestBody LoginUserDto userDto){
        return authService.createAuthToken(userDto);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> Registration(@RequestBody RegisterUserDto userDto){
        return authService.registerUser(userDto);
    }
}
