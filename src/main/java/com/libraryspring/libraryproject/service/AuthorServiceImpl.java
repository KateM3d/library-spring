package com.libraryspring.libraryproject.service;

import com.libraryspring.libraryproject.dto.AuthorDto;
import com.libraryspring.libraryproject.dto.BookDto;
import com.libraryspring.libraryproject.model.Author;
import com.libraryspring.libraryproject.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public AuthorDto getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow();
        AuthorDto authorDto = convertEntityToDto(author);
        return authorDto;

    }

    @Override
    public List<AuthorDto> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream()
                .map(author -> AuthorDto.builder()
                        .id(author.getId())
                        .name(author.getName())
                        .surname(author.getSurname())
                        .books(author.getBooks()
                                .stream()
                                .map(book -> BookDto.builder()
                                        .id(book.getId())
                                        .name(book.getName())
                                        .genre(book.getGenre()
                                                .getName())
                                        .build())
                                .collect(Collectors.toList()))

                        .build())
                .collect(Collectors.toList());
    }

    private AuthorDto convertEntityToDto(Author author) {

        List<BookDto> bookDtoList = author.getBooks()
                .stream()
                .map(book -> BookDto.builder()
                        .genre(book.getGenre()
                                .getName())
                        .name(book.getName())
                        .id(book.getId())
                        .build())
                .toList();

        AuthorDto authorDto = AuthorDto.builder()
                .id(author.getId())
                .name(author.getName())
                .surname(author.getSurname())
                .books(bookDtoList)
                .build();
        return authorDto;
    }
}
