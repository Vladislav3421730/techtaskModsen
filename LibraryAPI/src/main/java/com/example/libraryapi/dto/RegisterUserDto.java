package com.example.libraryapi.dto;

import lombok.Data;

@Data
public class RegisterUserDto {
    private Long id;
    private String username;
    private String password;
    private String name;
}
