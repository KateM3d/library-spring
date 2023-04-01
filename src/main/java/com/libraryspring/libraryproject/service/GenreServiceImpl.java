package com.libraryspring.libraryproject.service;

import com.libraryspring.libraryproject.dto.AuthorAndBookDto;
import com.libraryspring.libraryproject.dto.AuthorDto;
import com.libraryspring.libraryproject.dto.BookDto;
import com.libraryspring.libraryproject.dto.GenreDto;
import com.libraryspring.libraryproject.model.Author;
import com.libraryspring.libraryproject.model.Book;
import com.libraryspring.libraryproject.model.Genre;
import com.libraryspring.libraryproject.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public GenreDto getGenreById(Long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow();

        List<AuthorAndBookDto> authorAndBookDtoList = genre.getBooks()
                .stream()
                .map(book -> AuthorAndBookDto.builder()
                        .id(book.getId())
                        .name(book.getName())
                        .author(book.getAuthors()
                                .stream()
                                .map(author -> author.getName() + " " + author.getSurname())
                                .collect(Collectors.joining(", ")))
                        .build())
                .collect(Collectors.toList());

        GenreDto genreDto = new GenreDto();
        genreDto.setId(genre.getId());
        genreDto.setName(genre.getName());
        genreDto.setBooks(authorAndBookDtoList);
        return genreDto;
    }
}