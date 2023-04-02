package com.libraryspring.libraryproject.service;

import com.libraryspring.libraryproject.dto.BookDto;
import com.libraryspring.libraryproject.model.Book;
import com.libraryspring.libraryproject.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public BookDto getByNameV1(String name) {
        Book book = bookRepository.findBookByName(name)
                .orElseThrow();
        return convertEntityToDto(book);
    }

    @Override
    public BookDto getByNameV2(String name) {

        Book book = bookRepository.findBookByNameBySql(name)
                .orElseThrow();
        return convertEntityToDto(book);
    }

    private BookDto convertEntityToDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .genre(book.getGenre()
                        .getName())
                .name(book.getName())
                .build();
    }
}
