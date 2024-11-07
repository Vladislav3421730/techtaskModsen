package com.example.libraryapi.TestServices;

import com.example.libraryapi.dto.BookDto;
import com.example.libraryapi.exceptions.BookNotFoundException;
import com.example.libraryapi.models.Book;
import com.example.libraryapi.repositories.BookRepository;
import com.example.libraryapi.services.Impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;
    private BookDto bookDto;

    @BeforeEach
    public void setUp() {
        book = new Book();
        book.setId(1L);
        book.setName("Test Book");
        book.setISBN("123-456-789");

        bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setName("Test Book");
        bookDto.setIsbn("123-456-789");
    }

    @Test
    public void testSaveBook() {

        when(modelMapper.map(bookDto, Book.class)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(modelMapper.map(book, BookDto.class)).thenReturn(bookDto);


        BookDto result = bookService.save(bookDto);

        assertNotNull(result);
        assertEquals("Test Book", result.getName());
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    public void testUpdateBook() {

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);
        when(modelMapper.map(book, BookDto.class)).thenReturn(bookDto);

        BookDto result = bookService.update(bookDto);

        assertNotNull(result);
        assertEquals("Test Book", result.getName());
        verify(bookRepository).findById(1L);
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    public void testUpdateBook_NotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        BookNotFoundException thrown = assertThrows(BookNotFoundException.class, () -> bookService.update(bookDto));
        assertEquals("Book with id 1 wasn't found. Updating is impossible", thrown.getMessage());
    }

    @Test
    public void testDeleteBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        bookService.delete(1L);

        verify(bookRepository).delete(any(Book.class));
    }

    @Test
    public void testDeleteBook_NotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        BookNotFoundException thrown = assertThrows(BookNotFoundException.class, () -> bookService.delete(1L));
        assertEquals("Book with id 1 wasn't found. Deleting is impossible", thrown.getMessage());
    }

    @Test
    public void testFindAllBooks() {

        Book book2 = new Book();
        book2.setId(2L);
        book2.setName("Another Book");
        when(bookRepository.findAll()).thenReturn(List.of(book, book2));
        when(modelMapper.map(book, BookDto.class)).thenReturn(bookDto);

        List<BookDto> result = bookService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(bookRepository).findAll();
    }

    @Test
    public void testFindByIsbn() {
        when(bookRepository.findByISBN("123-456-789")).thenReturn(Optional.of(book));
        when(modelMapper.map(book, BookDto.class)).thenReturn(bookDto);

        BookDto result = bookService.findByIsbn("123-456-789");

        assertNotNull(result);
        assertEquals("Test Book", result.getName());
        verify(bookRepository).findByISBN("123-456-789");
    }

    @Test
    public void testFindByIsbn_NotFound() {
        when(bookRepository.findByISBN("123-456-789")).thenReturn(Optional.empty());

        BookNotFoundException thrown = assertThrows(BookNotFoundException.class, () -> bookService.findByIsbn("123-456-789"));
        assertEquals("Book with ISBN 123-456-789 wasn't found", thrown.getMessage());
    }

    @Test
    public void testFindById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(modelMapper.map(book, BookDto.class)).thenReturn(bookDto);

        BookDto result = bookService.findById(1L);

        assertNotNull(result);
        assertEquals("Test Book", result.getName());
        verify(bookRepository).findById(1L);
    }

    @Test
    public void testFindById_NotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        BookNotFoundException thrown = assertThrows(BookNotFoundException.class, () -> bookService.findById(1L));
        assertEquals("Book with id 1 wasn't found", thrown.getMessage());
    }

}
