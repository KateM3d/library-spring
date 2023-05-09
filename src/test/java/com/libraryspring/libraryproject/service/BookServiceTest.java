package com.libraryspring.libraryproject.service;

import com.libraryspring.libraryproject.dto.*;
import com.libraryspring.libraryproject.model.Book;
import com.libraryspring.libraryproject.model.Genre;
import com.libraryspring.libraryproject.repository.BookRepository;
import com.libraryspring.libraryproject.repository.GenreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BookServiceTest {
    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookServiceImpl bookService;

    @Mock
    GenreRepository genreRepository;


    @Test
    public void testGetBookByNameV1() {
        Long id = 1L;
        String name = "Peter Pan";

        Book book = new Book(id, name, new Genre(), new HashSet<>());
        when(bookRepository.findBookByName(name)).thenReturn(Optional.of(book));

        BookDto bookDto = bookService.getByNameV1(name);

        verify(bookRepository).findBookByName(name);

        Assertions.assertEquals(bookDto.getId(), book.getId());
        Assertions.assertEquals(bookDto.getName(), book.getName());
    }

    @Test
    public void testGetAuthorByNameV1Fail() {
        String name = "John";
        when(bookRepository.findBookByName(name)).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalStateException.class, () -> bookService.getByNameV1(name));
        verify(bookRepository).findBookByName(name);
    }

    @Test
    public void testGetBookByNameV2() {
        Long id = 1L;
        String name = "Peter Pan";

        Book book = new Book(id, name, new Genre(), new HashSet<>());
        when(bookRepository.findBookByNameBySql(name)).thenReturn(Optional.of(book));

        BookDto bookDto = bookService.getByNameV2(name);

        verify(bookRepository).findBookByNameBySql(name);

        Assertions.assertEquals(bookDto.getId(), book.getId());
        Assertions.assertEquals(bookDto.getName(), book.getName());
    }

    @Test
    public void testGetAuthorByNameV2Fail() {
        String name = "Peter Pan";
        when(bookRepository.findBookByNameBySql(name)).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalStateException.class, () -> bookService.getByNameV2(name));
        verify(bookRepository).findBookByNameBySql(name);
    }

    @Test
    public void testCreateBook() {
        Genre genre = new Genre("Fantasy");
        GenreCreateDto genreCreateDto = new GenreCreateDto("Fantasy");
        BookCreateDto bookCreateDto = new BookCreateDto("Peter Pan", genre);

        Book book = new Book(1L, "Peter Pan", genre, new HashSet<>());

        when(genreRepository.findByName(genreCreateDto.getName())).thenReturn(genre);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDto bookDto = bookService.createBook(bookCreateDto);

        verify(genreRepository).findByName(genreCreateDto.getName());
        verify(bookRepository).save(any(Book.class));
        Assertions.assertNotNull(bookDto);
        Assertions.assertEquals(book.getId(), bookDto.getId());
        Assertions.assertEquals(book.getName(), bookDto.getName());
    }


    @Test
    public void testUpdateBook() {
        Long id = 1L;
        String name = "Peter Pan";
        Genre genre = new Genre("Fantasy");

        Book book = new Book(id, name, genre, new HashSet<>());
        BookUpdateDto bookUpdateDto = new BookUpdateDto(id, "Alice in Wonderland", "Fantasy");

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);
        when(genreRepository.findByName(bookUpdateDto.getGenre())).thenReturn(genre);

        BookDto bookDto = bookService.updateBook(bookUpdateDto);

        verify(bookRepository).findById(id);
        verify(bookRepository).save(book);
        verify(genreRepository).findByName(bookUpdateDto.getGenre());
        Assertions.assertNotNull(bookDto);
        Assertions.assertEquals(book.getId(), bookDto.getId());
        Assertions.assertEquals(bookUpdateDto.getName(), bookDto.getName());
    }

    @Test
    public void testDeleteBook() {
        Long id = 1L;

        bookService.deleteBook(id);

        verify(bookRepository).deleteById(id);
    }

    @Test
    public void testGetAllBooks() {
        Long id = 1L;
        String name = "Peter Pan";
        List<Book> books = new ArrayList<>();
        books.add(new Book(id, name, new Genre(), new HashSet<>()));

        when(bookRepository.findAll()).thenReturn(books);

        List<BookDto> bookDtos = bookService.getAllBooks();

        verify(bookRepository).findAll();
        Assertions.assertNotNull(bookDtos);
        Assertions.assertEquals(books.size(), bookDtos.size());

        BookDto bookDto = bookDtos.get(0);
        Assertions.assertEquals(id, bookDto.getId());
        Assertions.assertEquals(name, bookDto.getName());
    }
}
