package com.example.libraryapi;


import com.example.libraryapi.controllers.BookController;
import com.example.libraryapi.dto.BookDto;
import com.example.libraryapi.exceptions.AppError;
import com.example.libraryapi.models.Book;
import com.example.libraryapi.repositories.BookRepository;
import com.example.libraryapi.services.BookService;
import com.example.libraryapi.utils.JwtTokenUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@WithMockUser
class BookControllerApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    private final ModelMapper modelMapper=new ModelMapper();

    @MockBean
    private JwtTokenUtils jwtTokenUtilsMock;

    @MockBean
    private BookService bookService;


    ObjectMapper objectMapper=new ObjectMapper();
    ObjectWriter objectWriter=objectMapper.writer();


    Book book1= new Book(1L,"12789812-11","name","genre","description","author");
    Book book2= new Book(2L,"12789812-12","name","genre","description","author");
    Book book3= new Book(3L,"12789812-13","name","genre","description","author");

    BookDto bookDto1=modelMapper.map(book1, BookDto.class);
    BookDto bookDto2=modelMapper.map(book2, BookDto.class);
    BookDto bookDto3 =modelMapper.map(book3, BookDto.class);


    @Test
    void testFindAllBooks() throws Exception {

        when(bookService.findAll()).thenReturn(List.of(bookDto1,bookDto2,bookDto3));

        mockMvc.perform(get("/book/get"))
                .andExpect(status().isOk())
                .andExpectAll(
                            jsonPath("$",hasSize(3)),
                            jsonPath("$[0].id",Matchers.is( 1)),
                            jsonPath("$[0].isbn",Matchers.is("12789812-11")),
                            jsonPath("$[0].name",Matchers.is("name")),
                            jsonPath("$[0].genre",Matchers.is("genre")),
                            jsonPath("$[0].description",Matchers.is("description")),
                            jsonPath("$[0].author",Matchers.is("author")
                            ));
    }


    @Test
    void testSaveBook() throws Exception{

        Book book=new Book(4L,"12789812-12","name","genre","description","author");
        BookDto addingBook=modelMapper.map(book, BookDto.class);

        when(bookService.save(addingBook)).thenReturn(addingBook);
        String content=objectWriter.writeValueAsString(addingBook);

        MockHttpServletRequestBuilder mockMvcRequestBuilders = post("/book/save")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content)
                .with(csrf());

        mockMvc.perform(mockMvcRequestBuilders)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpectAll(
                        jsonPath("$.id",Matchers.is( 4)),
                        jsonPath("$.isbn",Matchers.is("12789812-12")),
                        jsonPath("$.name",Matchers.is("name")),
                        jsonPath("$.genre",Matchers.is("genre")),
                        jsonPath("$.description",Matchers.is("description")),
                        jsonPath("$.author",Matchers.is("author")
                        ));
    }

    @Test
    void testFindBookById() throws Exception {

        when(bookService.findById(1L)).thenReturn(bookDto1);

        mockMvc.perform(get("/book/get/"+book1.getId()))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id",Matchers.is( 1)),
                        jsonPath("$.isbn",Matchers.is("12789812-11")),
                        jsonPath("$.name",Matchers.is("name")),
                        jsonPath("$.genre",Matchers.is("genre")),
                        jsonPath("$.description",Matchers.is("description")),
                        jsonPath("$.author",Matchers.is("author")
                        ));

    }

    @Test
    void testFindBookByISBN() throws Exception{

        when(bookService.findByIsbn("12789812-12")).thenReturn(bookDto2);

        mockMvc.perform(get("/book/get/isbn/"+bookDto2.getIsbn()))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id",Matchers.is( 2)),
                        jsonPath("$.isbn",Matchers.is("12789812-12")),
                        jsonPath("$.name",Matchers.is("name")),
                        jsonPath("$.genre",Matchers.is("genre")),
                        jsonPath("$.description",Matchers.is("description")),
                        jsonPath("$.author",Matchers.is("author")
                        ));
    }

    @Test
    public void testUpdateBook() throws Exception{

        Book book=new Book(1L,"12789812-12","NewName","NewGenre","description","author");
        BookDto updatingBook=modelMapper.map(book, BookDto.class);

        when(bookService.update(updatingBook)).thenReturn(updatingBook);

        String content=objectWriter.writeValueAsString(updatingBook);

        MockHttpServletRequestBuilder mockMvcRequestBuilders = put("/book/update")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content)
                .with(csrf());

        mockMvc.perform(mockMvcRequestBuilders)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpectAll(
                        jsonPath("$.id",Matchers.is( 1)),
                        jsonPath("$.isbn",Matchers.is("12789812-12")),
                        jsonPath("$.name",Matchers.is("NewName")),
                        jsonPath("$.genre",Matchers.is("NewGenre")),
                        jsonPath("$.description",Matchers.is("description")),
                        jsonPath("$.author",Matchers.is("author")
                        ));
    }

    @Test
    public void testDeleteBook() throws Exception {
        MockHttpServletRequestBuilder mockMvcRequestBuilders = delete("/book/delete/"+book1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(csrf());

        mockMvc.perform(mockMvcRequestBuilders)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpectAll(
                        jsonPath("$.message",Matchers.is("Book with id 1 has been deleted")
                ));
    }




    }
