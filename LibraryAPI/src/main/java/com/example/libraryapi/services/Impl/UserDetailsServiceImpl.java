package com.example.libraryapi.services.Impl;

import com.example.libraryapi.models.User;
import com.example.libraryapi.repositories.UserRepository;
import com.example.libraryapi.wrappers.UserDetailsWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Login with username %s wasn't find", username)));
        return new UserDetailsWrapper(user);
    }
}
