package com.example.libraryapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Токен после успешной авторизации")
public class JwtResponseDto {

    @Schema(description = "Токен", example = "48hcjs...")
    private String token;
}
