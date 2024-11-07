package com.example.libraryapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Сущность для входа пользователя в систему")
public class LoginUserDto {

    @Schema(description = "Логин пользователя",example = "username")
    private String username;

    @Schema(description = "Пароль пользователя",example = "password")
    private String password;
}
