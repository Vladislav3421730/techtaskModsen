package com.example.libraryapi.services;

import com.example.libraryapi.dto.RegisterUserDto;

public interface UserService {
    void save(RegisterUserDto registerUserDto);
}
