package com.libraryspring.libraryproject.service;

import com.libraryspring.libraryproject.dto.BookDto;

public interface BookService {
    BookDto getByNameV1(String name);

    BookDto getByNameV2(String name);
}
