package com.example.libraryapi.services.Impl;

import com.example.libraryapi.dto.JwtResponseDto;
import com.example.libraryapi.dto.LoginUserDto;
import com.example.libraryapi.dto.RegisterUserDto;
import com.example.libraryapi.dto.ResponseDto;
import com.example.libraryapi.exceptions.LoginFailedException;
import com.example.libraryapi.exceptions.RegistrationFailedException;
import com.example.libraryapi.models.User;
import com.example.libraryapi.repositories.UserRepository;
import com.example.libraryapi.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements com.example.libraryapi.services.AuthService {

    private final JwtTokenUtils jwtTokenUtils;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<JwtResponseDto> createAuthToken(LoginUserDto userDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));
        } catch (BadCredentialsException badCredentialsException) {
            throw new LoginFailedException(String.format("Invalid username or password for user %s", userDto.getUsername()));
        }

        User user = userRepository.findByUsername(userDto.getUsername()).get();
        return ResponseEntity.ok(new JwtResponseDto(jwtTokenUtils.generateToken(user)));
    }

    @Override
    public ResponseEntity<ResponseDto> registerUser(RegisterUserDto userDto) {
        if(userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new RegistrationFailedException(String.format("User with login %s already exist is system", userDto.getUsername()));
        }
        userRepository.save(modelMapper.map(userDto, User.class));
        return ResponseEntity.ok(new ResponseDto("User has been saved"));
    }
}
