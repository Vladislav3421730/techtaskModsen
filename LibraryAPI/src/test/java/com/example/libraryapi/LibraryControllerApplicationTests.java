package com.example.libraryapi;

import com.example.libraryapi.controllers.LibraryController;
import com.example.libraryapi.dto.BookDto;
import com.example.libraryapi.models.Book;
import com.example.libraryapi.services.LibraryService;
import com.example.libraryapi.utils.JwtTokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LibraryController.class)
@WithMockUser
public class LibraryControllerApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    private final ModelMapper modelMapper=new ModelMapper();

    @MockBean
    private JwtTokenUtils jwtTokenUtilsMock;

    @MockBean
    private LibraryService libraryService;

    ObjectMapper objectMapper=new ObjectMapper();
    ObjectWriter objectWriter=objectMapper.writer();


    Book book1= new Book(1L,"12789812-11","name","genre","description","author");
    Book book2= new Book(2L,"12789812-12","name","genre","description","author");
    Book book3= new Book(3L,"12789812-13","name","genre","description","author");

    BookDto bookDto1=modelMapper.map(book1, BookDto.class);
    BookDto bookDto2=modelMapper.map(book2, BookDto.class);
    BookDto bookDto3 =modelMapper.map(book3, BookDto.class);

    @Test
    void testFindFreeBooks() throws Exception {

        when(libraryService.findAvailableBooks()).thenReturn(List.of(bookDto1,bookDto2,bookDto3));

        mockMvc.perform(get("/library/get/free"))
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

}
