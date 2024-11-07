package com.example.libraryapi.TestServices;


import com.example.libraryapi.dto.BookDto;
import com.example.libraryapi.dto.BookStatusDto;
import com.example.libraryapi.exceptions.BookStatusNotFoundException;
import com.example.libraryapi.models.Book;
import com.example.libraryapi.models.BookStatus;
import com.example.libraryapi.repositories.BookRepository;
import com.example.libraryapi.repositories.BookStatusRepository;
import com.example.libraryapi.services.Impl.LibraryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class LibraryServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookStatusRepository bookStatusRepository;

    @Mock
    private ModelMapper modelMapper;

    private final ModelMapper mapper=new ModelMapper();

    @InjectMocks
    private LibraryServiceImpl libraryService;

    private Book book;
    private BookDto bookDto;
    private BookStatus bookStatus;
    private BookStatusDto bookStatusDto;

    @BeforeEach
    public void setUp() {
        book = new Book(1L, "123-456-789", "Test Book", "Fiction", "A test book description", "Test Author");
        bookDto = mapper.map(book, BookDto.class);

        bookStatus = BookStatus.builder()
                .id(1L)
                .borrowedAt(LocalDateTime.now().minusDays(3))
                .dueDate(LocalDateTime.now().plusDays(4))
                .book(book)
                .build();

        bookStatusDto=mapper.map(bookStatus, BookStatusDto.class);
    }

    @Test
    public void testFindAvailableBooks() {
        when(bookRepository.findAllFree()).thenReturn(List.of(book));
        when(modelMapper.map(book, BookDto.class)).thenReturn(bookDto);

        List<BookDto> result = libraryService.findAvailableBooks();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Book", result.get(0).getName());
        verify(bookRepository).findAllFree();
    }

    @Test
    public void testFindAllBookStatuses() {
        when(bookStatusRepository.findAll()).thenReturn(List.of(bookStatus));
        when(modelMapper.map(bookStatus, BookStatusDto.class)).thenReturn(bookStatusDto);

        List<BookStatusDto> result = libraryService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Book", result.get(0).getBook().getName());
        verify(bookStatusRepository).findAll();
    }

    @Test
    public void testFindBookStatusById() {
        when(bookStatusRepository.findById(1L)).thenReturn(Optional.of(bookStatus));
        when(modelMapper.map(bookStatus, BookStatusDto.class)).thenReturn(bookStatusDto);

        BookStatusDto result = libraryService.findBookStatusById(1L);

        assertNotNull(result);
        assertEquals("Test Book", result.getBook().getName());
        assertEquals(bookStatus.getBorrowedAt(), result.getBorrowedAt());
        verify(bookStatusRepository).findById(1L);
    }

    @Test
    public void testFindBookStatusById_NotFound() {
        when(bookStatusRepository.findById(1L)).thenReturn(Optional.empty());

        BookStatusNotFoundException thrown = assertThrows(BookStatusNotFoundException.class, () -> libraryService.findBookStatusById(1L));
        assertEquals("Status with id 1 wasn't found", thrown.getMessage());
    }

    @Test
    public void testUpdateBookStatus() {

        when(bookStatusRepository.findById(1L)).thenReturn(Optional.of(bookStatus));
        when(bookStatusRepository.save(bookStatus)).thenReturn(bookStatus);
        when(modelMapper.map(bookStatus, BookStatusDto.class)).thenReturn(bookStatusDto);

        BookStatusDto result = libraryService.updateBookStatus(bookStatusDto);
        assertNotNull(result);
        assertEquals(bookStatus.getBorrowedAt(), result.getBorrowedAt());
        assertEquals(bookStatus.getDueDate(), result.getDueDate());
        verify(bookStatusRepository).findById(1L);
        verify(bookStatusRepository).save(any(BookStatus.class));
    }

    @Test
    public void testUpdateBookStatus_NotFound() {

        when(bookStatusRepository.findById(1L)).thenReturn(Optional.empty());
        BookStatusNotFoundException thrown = assertThrows(BookStatusNotFoundException.class, () -> libraryService.updateBookStatus(bookStatusDto));
        assertEquals("Status with id 1 wasn't found. Updating is impossible", thrown.getMessage());
    }
}
