package com.example.libraryapi.TestServices;

import com.example.libraryapi.dto.JwtResponseDto;
import com.example.libraryapi.dto.LoginUserDto;
import com.example.libraryapi.exceptions.LoginFailedException;
import com.example.libraryapi.models.User;
import com.example.libraryapi.repositories.UserRepository;
import com.example.libraryapi.services.Impl.AuthServiceImpl;
import com.example.libraryapi.utils.JwtTokenUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private JwtTokenUtils jwtTokenUtils;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthServiceImpl authService;

    private LoginUserDto loginUserDto;

    @BeforeEach
    void setUp() {
        loginUserDto = new LoginUserDto();
        loginUserDto.setUsername("user");
        loginUserDto.setPassword("password123");
    }

    @Test
    void testCreateAuthToken_Success() {
        User user = new User();
        user.setUsername("user");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken("user","password"));
        when(userRepository.findByUsername(loginUserDto.getUsername())).thenReturn(Optional.of(user));
        when(jwtTokenUtils.generateToken(user)).thenReturn("valid token");

        ResponseEntity<JwtResponseDto> response = authService.createAuthToken(loginUserDto);

        assertNotNull(response);
        assertEquals("valid token", response.getBody().getToken());

        verify(userRepository).findByUsername(loginUserDto.getUsername());
        verify(jwtTokenUtils).generateToken(user);
    }

    @Test
    void testCreateAuthToken_Failure_InvalidCredentials() {

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(BadCredentialsException.class);

        LoginFailedException exception = assertThrows(LoginFailedException.class, () -> {
            authService.createAuthToken(loginUserDto);
        });

        assertEquals("Invalid username or password for user user", exception.getMessage());

        verify(userRepository, never()).findByUsername(loginUserDto.getUsername());
    }

}
