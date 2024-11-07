package com.example.libraryapi.TestControllers;


import com.example.libraryapi.controllers.AuthController;
import com.example.libraryapi.dto.*;
import com.example.libraryapi.services.AuthService;
import com.example.libraryapi.utils.JwtTokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@WithMockUser
public class AuthControllerTests {

    @Autowired
    private MockMvc mockMvc;

    private final static ModelMapper modelMapper = new ModelMapper();

    @MockBean
    private JwtTokenUtils jwtTokenUtilsMock;

    @MockBean
    private AuthService authService;

    private final static ObjectMapper objectMapper = new ObjectMapper();
    private final static ObjectWriter objectWriter = objectMapper.writer();

    @Test
    void testLogin() throws Exception {

        LoginUserDto loginUserDto= LoginUserDto.builder()
                .username("user")
                .password("q1w2e3")
                .build();

        String content = objectWriter.writeValueAsString(loginUserDto);

        when(authService.createAuthToken(loginUserDto)).thenReturn(ResponseEntity.ok(new JwtResponseDto("valid token")));

        MockHttpServletRequestBuilder mockMvcRequestBuilders = post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content)
                .with(csrf());

        mockMvc.perform(mockMvcRequestBuilders)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.token", is("valid token")));
    }

    @Test
    void testRegisterUserSuccess() throws Exception {

        RegisterUserDto registerUserDto = RegisterUserDto.builder()
                .name("Vlad")
                .username("User")
                .password("q1w2e3")
                .build();


        String content = objectWriter.writeValueAsString(registerUserDto);

        when(authService.registerUser(registerUserDto)).thenReturn(ResponseEntity.ok(new ResponseDto("User has been saved")));

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(content)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("User has been saved")));

        verify(authService, times(1)).registerUser(registerUserDto);
    }





}
