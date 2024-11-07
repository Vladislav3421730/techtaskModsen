package com.example.libraryapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Сущность для отправки ответов в случае статуса 200")
public class ResponseDto {

    @Schema(description = "Сообщение")
    private String message;
}
