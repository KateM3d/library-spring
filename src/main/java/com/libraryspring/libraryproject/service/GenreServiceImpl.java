package com.libraryspring.libraryproject.service;

import com.libraryspring.libraryproject.dto.*;
import com.libraryspring.libraryproject.model.Book;
import com.libraryspring.libraryproject.model.Genre;
import com.libraryspring.libraryproject.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public GenreBookWithAuthorDto getGenreById(Long id) {
        log.info("Trying to find genre by id {}", id);
        Optional<Genre> genre = genreRepository.findById(id);
        if (genre.isPresent()) {
            log.info("Found genre with id {}", id);
            List<AuthorAndBookDto> authorAndBookDtoList = genre.get()
                    .getBooks()
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
            genreBookWithAuthorDto.setId(genre.get()
                    .getId());
            genreBookWithAuthorDto.setName(genre.get()
                    .getName());
            genreBookWithAuthorDto.setBooks(authorAndBookDtoList);
            return genreBookWithAuthorDto;
        } else {
            log.error("Genre with id {} not found", id);
            throw new IllegalStateException("Genre not found");
        }
    }

    @Override
    public List<GenreDto> getAllGenres() {
        log.info("Getting all genres");
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
                                        .genre(book.getGenre()
                                                .getName())
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }
}