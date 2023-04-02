package com.libraryspring.libraryproject.service;

import com.libraryspring.libraryproject.dto.AuthorDto;
import com.libraryspring.libraryproject.dto.GenreDto;

import java.util.List;

public interface AuthorService {
    AuthorDto getAuthorById(Long id);
    List<AuthorDto> getAllAuthors();
}
