package com.libraryspring.libraryproject.service;

import com.libraryspring.libraryproject.dto.AuthorCreateDto;
import com.libraryspring.libraryproject.dto.AuthorDto;
import com.libraryspring.libraryproject.dto.AuthorUpdateDto;
import com.libraryspring.libraryproject.model.Author;
import com.libraryspring.libraryproject.model.Book;
import com.libraryspring.libraryproject.repository.AuthorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;


import java.util.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthorServiceTest {
    @Mock
    AuthorRepository authorRepository;

    @InjectMocks
    AuthorServiceImpl authorService;

    @Test
    public void testGetAuthorById() {
        Long id = 1L;
        String name = "John";
        String surname = "Doe";
        Set<Book> books = new HashSet<>();

        Author author = new Author(id, name, surname, books);

        when(authorRepository.findById(id)).thenReturn(Optional.of(author));

        AuthorDto authorDto = authorService.getAuthorById(id);
        verify(authorRepository).findById(id);

        Assertions.assertEquals(authorDto.getId(), author.getId());
        Assertions.assertEquals(authorDto.getName(), author.getName());
        Assertions.assertEquals(authorDto.getSurname(), author.getSurname());
    }

    @Test
    public void testGetAuthorByIdFailed() {
        Long id = 1L;
        when(authorRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalStateException.class, () -> authorService.getAuthorById(id));
        verify(authorRepository).findById(id);
    }

    @Test
    public void testGetAuthorByNameV1() {
        Long id = 1L;
        String name = "John";
        String surname = "Doe";
        Set<Book> books = new HashSet<>();

        Author author = new Author(id, name, surname, books);
        when(authorRepository.findAuthorByName(name)).thenReturn(Optional.of(author));

        AuthorDto authorDto = authorService.getByNameV1(name);

        verify(authorRepository).findAuthorByName(name);

        Assertions.assertEquals(authorDto.getId(), author.getId());
        Assertions.assertEquals(authorDto.getName(), author.getName());
        Assertions.assertEquals(authorDto.getSurname(), author.getSurname());
    }

    @Test
    public void testGetAuthorByNameV1Fail() {
        String name = "John";
        when(authorRepository.findAuthorByName(name)).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalStateException.class, () -> authorService.getByNameV1(name));
        verify(authorRepository).findAuthorByName(name);
    }

    @Test
    public void testGetAuthorByNameV2() {
        Long id = 1L;
        String name = "John";
        String surname = "Doe";
        Set<Book> books = new HashSet<>();

        Author author = new Author(id, name, surname, books);
        when(authorRepository.findAuthorByNameBySql(name)).thenReturn(Optional.of(author));

        AuthorDto authorDto = authorService.getByNameV2(name);

        verify(authorRepository).findAuthorByNameBySql(name);

        Assertions.assertEquals(authorDto.getId(), author.getId());
        Assertions.assertEquals(authorDto.getName(), author.getName());
        Assertions.assertEquals(authorDto.getSurname(), author.getSurname());
    }

    @Test
    public void testGetAuthorByNameV2Fail() {
        String name = "John";
        when(authorRepository.findAuthorByNameBySql(name)).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalStateException.class, () -> authorService.getByNameV2(name));
        verify(authorRepository).findAuthorByNameBySql(name);
    }

    @Test
    public void testCreateAuthor() {
        AuthorCreateDto authorCreateDto = new AuthorCreateDto("John", "Does");
        Author author = new Author(1L, authorCreateDto.getName(), authorCreateDto.getSurname(), new HashSet<>());
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        AuthorDto authorDto = authorService.createAuthor(authorCreateDto);

        verify(authorRepository).save(any(Author.class));
        Assertions.assertNotNull(authorDto);
        Assertions.assertEquals(author.getId(), authorDto.getId());
        Assertions.assertEquals(author.getName(), authorDto.getName());
        Assertions.assertEquals(author.getSurname(), authorDto.getSurname());
    }

    @Test
    public void testUpdateAuthor() {
        Long id = 1L;
        String name = "John";
        String surname = "Doe";
        AuthorUpdateDto authorUpdateDto = new AuthorUpdateDto(id, "Jane", "Doe");
        Author author = new Author(id, name, surname, new HashSet<>());
        when(authorRepository.findById(id)).thenReturn(Optional.of(author));
        when(authorRepository.save(author)).thenReturn(author);

        AuthorDto authorDto = authorService.updateAuthor(authorUpdateDto);

        verify(authorRepository).findById(id);
        verify(authorRepository).save(author);
        Assertions.assertNotNull(authorDto);
        Assertions.assertEquals(author.getId(), authorDto.getId());
        Assertions.assertEquals(authorUpdateDto.getName(), authorDto.getName());
        Assertions.assertEquals(authorUpdateDto.getSurname(), authorDto.getSurname());
    }

    @Test
    public void testDeleteAuthor() {
        Long id = 1L;

        authorService.deleteAuthor(id);

        verify(authorRepository).deleteById(id);
    }

    @Test
    public void testGetAllAuthors() {
        Long id = 1L;
        String name = "John";
        String surname = "Doe";
        Author author = new Author(id, name, surname, new HashSet<>());
        List<Author> authors = Collections.singletonList(author);
        when(authorRepository.findAll()).thenReturn(authors);

        List<AuthorDto> authorDtos = authorService.getAllAuthors();

        verify(authorRepository).findAll();
        Assertions.assertNotNull(authorDtos);
        Assertions.assertEquals(authors.size(), authorDtos.size());

        AuthorDto authorDto = authorDtos.get(0);
        Assertions.assertEquals(author.getId(), authorDto.getId());
        Assertions.assertEquals(author.getName(), authorDto.getName());
        Assertions.assertEquals(author.getSurname(), authorDto.getSurname());
    }
}
