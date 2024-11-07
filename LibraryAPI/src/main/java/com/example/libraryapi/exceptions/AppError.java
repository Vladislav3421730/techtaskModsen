package com.example.libraryapi.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


@Data
@Schema(description = "Сущность для отправки ответа в случае ошибки")
public class AppError {

    @Schema(description = "Сообщение ошибки")
    private String message;

    @Schema(description = "Время возникновения ошибки", example = "2024-11-07 10:30:00")
    private String timestamp;

    public AppError(String message){
        this.message=message;
        this.timestamp=formatTimestamp();
    }

    private String formatTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("ru", "RU"));
        sdf.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        return sdf.format(new Date());
    }
}

