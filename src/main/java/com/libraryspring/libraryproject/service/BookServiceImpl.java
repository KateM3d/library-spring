package com.libraryspring.libraryproject.service;

import com.libraryspring.libraryproject.dto.AuthorDto;
import com.libraryspring.libraryproject.dto.BookCreateDto;
import com.libraryspring.libraryproject.dto.BookDto;
import com.libraryspring.libraryproject.dto.BookUpdateDto;
import com.libraryspring.libraryproject.model.Author;
import com.libraryspring.libraryproject.model.Book;
import com.libraryspring.libraryproject.model.Genre;
import com.libraryspring.libraryproject.repository.BookRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public BookDto getByNameV3(String name) {
        Specification<Book> specification = Specification.where(new Specification<Book>() {
            @Override
            public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("name"), name);
            }
        });

        Book book = bookRepository.findOne(specification)
                .orElseThrow();
        return convertEntityToDto(book);
    }

    @Override
    public BookDto createBook(BookCreateDto bookCreateDto) {
        Book book = bookRepository.save(convertDtoToEntity(bookCreateDto));
        BookDto bookDto = convertEntityToDto(book);
        return bookDto;
    }

    @Override
    public BookDto updateBook(BookUpdateDto bookUpdateDto) {

        Book book = bookRepository.findById(bookUpdateDto.getId())
                .orElseThrow();
        book.setName(bookUpdateDto.getName());
        book.setGenre(bookUpdateDto.getGenre());

        Book savedBook = bookRepository.save(book);
        BookDto bookDto = convertEntityToDto(savedBook);
        return bookDto;
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    private Book convertDtoToEntity(BookCreateDto bookCreateDto) {
        return Book.builder()
                .name(bookCreateDto.getName())
                .genre(bookCreateDto.getGenre())
                .build();
    }

    private BookDto convertEntityToDto(Book book) {

        BookDto bookDto = BookDto.builder()
                .id(book.getId())
                .genre(book.getGenre()
                        .getName())
                .name(book.getName())
                .build();
        return bookDto;
    }

}
