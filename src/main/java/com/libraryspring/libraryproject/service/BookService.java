package com.libraryspring.libraryproject.service;

import com.libraryspring.libraryproject.dto.*;

import java.util.List;

public interface BookService {

    List<BookDto> getAllBooks();

    BookDto getByNameV1(String name);

    BookDto getByNameV2(String name);

    BookDto getByNameV3(String name);

    BookDto createBook(BookCreateDto authorCreateDto);

    BookDto updateBook(BookUpdateDto bookUpdateDto);

    void deleteBook(Long id);

}
