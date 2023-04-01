package com.libraryspring.libraryproject.service;

import com.libraryspring.libraryproject.dto.*;
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
    public GenreBookWithAuthorDto getGenreById(Long id) {
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

        GenreBookWithAuthorDto genreBookWithAuthorDto = new GenreBookWithAuthorDto();
        genreBookWithAuthorDto.setId(genre.getId());
        genreBookWithAuthorDto.setName(genre.getName());
        genreBookWithAuthorDto.setBooks(authorAndBookDtoList);
        return genreBookWithAuthorDto;
    }

    @Override
    public List<GenreDto> getAllGenres() {
        List<Genre> genres = genreRepository.findAll();
        return genres.stream()
                .map(genre -> GenreDto.builder()
                        .id(genre.getId())
                        .name(genre.getName())
                        .books(genre.getBooks()
                                .stream()
                                .map(book -> BookDto.builder()
                                        .id(book.getId())
                                        .name(book.getName())
                                        .genre(book.getGenre().getName())
                                        .build())
                                .collect(Collectors.toList()))

                        .build())
                .collect(Collectors.toList());    }
}