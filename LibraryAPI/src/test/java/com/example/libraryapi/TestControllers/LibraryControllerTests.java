package com.example.libraryapi.TestControllers;

import com.example.libraryapi.controllers.LibraryController;
import com.example.libraryapi.dto.BookDto;
import com.example.libraryapi.dto.BookStatusDto;
import com.example.libraryapi.models.Book;
import com.example.libraryapi.models.BookStatus;
import com.example.libraryapi.services.LibraryService;
import com.example.libraryapi.utils.JwtTokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LibraryController.class)
@WithMockUser
public class LibraryControllerTests {

    @Autowired
    private MockMvc mockMvc;

    private final static ModelMapper modelMapper=new ModelMapper();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @MockBean
    private JwtTokenUtils jwtTokenUtilsMock;

    @MockBean
    private LibraryService libraryService;

    private final static ObjectMapper objectMapper=new ObjectMapper();
    private static ObjectWriter objectWriter;

    private static BookDto bookDto1,bookDto2,bookDto3;
    private static BookStatusDto bookStatusDto1,bookStatusDto2,bookStatusDto3,updatingBookStatusDto;

    @BeforeAll
    static void setUp() {
        Book  book1 = new Book(1L, "12789812-11", "The Great Adventure", "Adventure", "An epic journey.", "John Doe");
        Book book2 = new Book(2L, "12789812-12", "Mystery of the Lake", "Mystery", "Secrets beneath the surface.", "Jane Smith");
        Book book3 = new Book(3L, "12789812-13", "Science Explained", "Education", "A dive into scientific concepts.", "Dr. Science");

        BookStatus bookStatus1=new BookStatus(1L, LocalDateTime.parse("2024-10-06 21:10:42",formatter), LocalDateTime.parse("2024-12-06 21:10:42",formatter),book1);
        BookStatus bookStatus2=new BookStatus(2L,LocalDateTime.parse("2024-11-03 21:10:42",formatter), LocalDateTime.parse("2024-12-06 21:10:42",formatter),book2);
        BookStatus bookStatus3=new BookStatus(3L,LocalDateTime.parse("2023-11-01 10:00:00",formatter), LocalDateTime.parse("2024-12-06 21:10:42",formatter),book3);
        BookStatus updatingBookStatus=new BookStatus(3L,LocalDateTime.parse("2023-10-01 10:00:00",formatter), LocalDateTime.parse("2024-12-23 21:10:42",formatter),book3);

        bookDto1 = modelMapper.map(book1, BookDto.class);
        bookDto2 = modelMapper.map(book2, BookDto.class);
        bookDto3 = modelMapper.map(book3, BookDto.class);

        bookStatusDto1=modelMapper.map(bookStatus1,BookStatusDto.class);
        bookStatusDto2=modelMapper.map(bookStatus2,BookStatusDto.class);
        bookStatusDto3=modelMapper.map(bookStatus3,BookStatusDto.class);
        updatingBookStatusDto=modelMapper.map(updatingBookStatus,BookStatusDto.class);

        objectMapper.registerModule(new JavaTimeModule());
        objectWriter = objectMapper.writer();

    }

    @Test
    void testFindFreeBooks() throws Exception {

        when(libraryService.findAvailableBooks()).thenReturn(List.of(bookDto1,bookDto2,bookDto3));

        mockMvc.perform(get("/library/get/free"))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$", hasSize(3)),
                        jsonPath("$[0].id", is(1)),
                        jsonPath("$[0].isbn", is("12789812-11")),
                        jsonPath("$[0].name", is("The Great Adventure")),
                        jsonPath("$[0].genre", is("Adventure")),
                        jsonPath("$[0].description", is("An epic journey.")),
                        jsonPath("$[0].author", is("John Doe")),
                        jsonPath("$[1].id", is(2)),
                        jsonPath("$[1].isbn", is("12789812-12")),
                        jsonPath("$[1].name", is("Mystery of the Lake")),
                        jsonPath("$[1].genre", is("Mystery")),
                        jsonPath("$[1].description", is("Secrets beneath the surface.")),
                        jsonPath("$[1].author", is("Jane Smith")),
                        jsonPath("$[2].id", is(3)),
                        jsonPath("$[2].isbn", is("12789812-13")),
                        jsonPath("$[2].name", is("Science Explained")),
                        jsonPath("$[2].genre", is("Education")),
                        jsonPath("$[2].description", is("A dive into scientific concepts.")),
                        jsonPath("$[2].author", is("Dr. Science")
                        ));
    }

    @Test
    void testFindBookStatus() throws Exception {

        when(libraryService.findAll()).thenReturn(List.of(bookStatusDto1,bookStatusDto2,bookStatusDto3));

        mockMvc.perform(get("/library/get"))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$", hasSize(3)),
                        jsonPath("$[0].id",is(1)),
                        jsonPath("$[0].borrowedAt",is("2024-10-06 21:10:42")),
                        jsonPath("$[0].dueDate",is("2024-12-06 21:10:42")),
                        jsonPath("$[0].book.id", is(1)),
                        jsonPath("$[0].book.isbn", is("12789812-11")),
                        jsonPath("$[0].book.name", is("The Great Adventure")),
                        jsonPath("$[0].book.genre", is("Adventure")),
                        jsonPath("$[0].book.description", is("An epic journey.")),
                        jsonPath("$[0].book.author", is("John Doe")),
                        jsonPath("$[1].id",is(2)),
                        jsonPath("$[1].borrowedAt",is("2024-11-03 21:10:42")),
                        jsonPath("$[1].dueDate",is("2024-12-06 21:10:42")),
                        jsonPath("$[1].book.id", is(2)),
                        jsonPath("$[1].book.isbn", is("12789812-12")),
                        jsonPath("$[1].book.name", is("Mystery of the Lake")),
                        jsonPath("$[1].book.genre", is("Mystery")),
                        jsonPath("$[1].book.description", is("Secrets beneath the surface.")),
                        jsonPath("$[1].book.author", is("Jane Smith")),
                        jsonPath("$[2].id",is(3)),
                        jsonPath("$[2].borrowedAt",is("2023-11-01 10:00:00")),
                        jsonPath("$[2].dueDate",is("2024-12-06 21:10:42")),
                        jsonPath("$[2].book.isbn", is("12789812-13")),
                        jsonPath("$[2].book.name", is("Science Explained")),
                        jsonPath("$[2].book.genre", is("Education")),
                        jsonPath("$[2].book.description", is("A dive into scientific concepts.")),
                        jsonPath("$[2].book.author", is("Dr. Science"))
                        );
    }

    @Test
    void testFindBookStatusById() throws Exception {

        when(libraryService.findBookStatusById(bookStatusDto1.getId())).thenReturn(bookStatusDto1);

        mockMvc.perform(get("/library/get/"+bookStatusDto1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpectAll(
                        jsonPath("$.id",is(1)),
                        jsonPath("$.borrowedAt",is("2024-10-06 21:10:42")),
                        jsonPath("$.dueDate",is("2024-12-06 21:10:42")),
                        jsonPath("$.book.id", is(1)),
                        jsonPath("$.book.isbn", is("12789812-11")),
                        jsonPath("$.book.name", is("The Great Adventure")),
                        jsonPath("$.book.genre", is("Adventure")),
                        jsonPath("$.book.description", is("An epic journey.")),
                        jsonPath("$.book.author", is("John Doe"))
                );
    }

    @Test
    void testUpdateBookStatus() throws Exception{

        when(libraryService.updateBookStatus(updatingBookStatusDto)).thenReturn(updatingBookStatusDto);

        String content = objectWriter.writeValueAsString(updatingBookStatusDto);

        MockHttpServletRequestBuilder mockMvcRequestBuilders = put("/library/update")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content)
                .with(csrf());

        mockMvc.perform(mockMvcRequestBuilders)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpectAll(
                        jsonPath("$.borrowedAt",is("2023-10-01 10:00:00")),
                        jsonPath("$.dueDate",is("2024-12-23 21:10:42")),
                        jsonPath("$.book.isbn", is("12789812-13")),
                        jsonPath("$.book.name", is("Science Explained")),
                        jsonPath("$.book.genre", is("Education")),
                        jsonPath("$.book.description", is("A dive into scientific concepts.")),
                        jsonPath("$.book.author", is("Dr. Science"))
                );


    }

}
