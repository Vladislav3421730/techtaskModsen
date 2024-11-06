package com.example.libraryapi.services.Impl;

import com.example.libraryapi.dto.RegisterUserDto;
import com.example.libraryapi.models.User;
import com.example.libraryapi.models.enums.Role;
import com.example.libraryapi.repositories.UserRepository;
import com.example.libraryapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void save(RegisterUserDto userDto) {
        User user=modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(Role.ROLE_USER));
        userRepository.save(user);
    }
}
