package com.libraryspring.libraryproject.service;

import com.libraryspring.libraryproject.dto.AuthorDto;

public interface AuthorService {
    AuthorDto getAuthorById(Long id);
}
