package com.libraryspring.libraryproject.service;

import com.libraryspring.libraryproject.dto.*;

public interface BookService {
    BookDto getByNameV1(String name);

    BookDto getByNameV2(String name);

    BookDto getByNameV3(String name);

    BookDto createBook(BookCreateDto authorCreateDto);

    BookDto updateBook(BookUpdateDto bookUpdateDto);

}
