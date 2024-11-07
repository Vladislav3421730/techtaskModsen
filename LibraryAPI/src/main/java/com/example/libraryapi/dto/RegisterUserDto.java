package com.example.libraryapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Сущность для регистрации нового пользователя")
public class RegisterUserDto {

    @Schema(description = "Логин нового пользователя",example = "username")
    private String username;

    @Schema(description = "Пароль нового пользователя",example = "password")
    private String password;

    @Schema(description = "Имя нового пользователя",example = "name")
    private String name;
}
