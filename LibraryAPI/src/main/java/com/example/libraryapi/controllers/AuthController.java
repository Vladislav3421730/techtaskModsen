package com.example.libraryapi.controllers;

import com.example.libraryapi.dto.JwtResponseDto;
import com.example.libraryapi.dto.LoginUserDto;
import com.example.libraryapi.dto.RegisterUserDto;
import com.example.libraryapi.dto.ResponseDto;
import com.example.libraryapi.exceptions.AppError;
import com.example.libraryapi.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Эндпоинты для авторизации и регистрации",description = "Можете войти как USER или ADMIN, или зарегистрироваться")
public class AuthController {

    private final AuthService authService;


    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Токен при успешной авторизации",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = JwtResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный логин или пароль",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AppError.class)
                    )
            )
    })
    @Operation(summary = "Вход пользователя в систему", description = "При успешной авторизации, получаете токен")
    @PostMapping(value = "/login", consumes = "application/json",produces = "application/json")
    public ResponseEntity<JwtResponseDto> createToken(@RequestBody LoginUserDto userDto){
        return authService.createAuthToken(userDto);
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Сообщение об успешной авторизации",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Авторизация не прошла, такой пользователь уже есть в системе",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AppError.class)
                    )
            )
    })
    @Operation(summary = "Регистрация нового пользователя")
    @PostMapping(value = "/register",consumes = "application/json",produces = "application/json")
    public ResponseEntity<ResponseDto> Registration(@RequestBody RegisterUserDto userDto){
        return authService.registerUser(userDto);
    }
}
