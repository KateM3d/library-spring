package com.libraryspring.libraryproject.service;

import com.libraryspring.libraryproject.dto.*;

import java.util.List;

public interface AuthorService {

    List<AuthorDto> getAllAuthors();

    AuthorDto getAuthorById(Long id);

    AuthorDto getByNameV1(String name);

    AuthorDto getByNameV2(String name);

    AuthorDto getByNameV3(String name);

    AuthorDto createAuthor(AuthorCreateDto authorCreateDto);

    AuthorDto updateAuthor(AuthorUpdateDto authorUpdateDto);

    void deleteAuthor(Long id);
}
