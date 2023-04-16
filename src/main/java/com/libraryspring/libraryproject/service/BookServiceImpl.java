package com.libraryspring.libraryproject.service;

import com.libraryspring.libraryproject.dto.BookCreateDto;
import com.libraryspring.libraryproject.dto.BookDto;
import com.libraryspring.libraryproject.dto.BookUpdateDto;
import com.libraryspring.libraryproject.model.Book;
import com.libraryspring.libraryproject.model.Genre;
import com.libraryspring.libraryproject.repository.BookRepository;
import com.libraryspring.libraryproject.repository.GenreRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;

    @Override
    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

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
        Genre genre = genreRepository.findByName(bookCreateDto.getGenre().getName());
        if (genre == null) {
            genre = new Genre(bookCreateDto.getGenre().getName());
            genre = genreRepository.save(genre);
        }

        Book book = Book.builder()
                .name(bookCreateDto.getName())
                .genre(genre)
                .build();

        book = bookRepository.save(book);

        BookDto bookDto = convertEntityToDto(book);
        return bookDto;
    }

    @Override
    public BookDto updateBook(BookUpdateDto bookUpdateDto) {
        Genre genre = genreRepository.findByName(bookUpdateDto.getGenre());
        if (genre == null) {
            genre = Genre.builder()
                    .name(bookUpdateDto.getGenre())
                    .build();
            genre = genreRepository.save(genre);
        }

        Book book = bookRepository.findById(bookUpdateDto.getId())
                .orElseThrow();
        book.setName(bookUpdateDto.getName());
        book.setGenre(genre);

        Book savedBook = bookRepository.save(book);
        BookDto bookDto = convertEntityToDto(savedBook);
        return bookDto;
    }


    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    private Book convertDtoToEntity(BookCreateDto bookCreateDto) {
        Genre genre = bookCreateDto.getGenre();
        final String genreName = genre.getName();

        if (genre.getId() == null) {
            Optional<Genre> optionalGenre = Optional.ofNullable(genreRepository.findByName(genre.getName()));
            genre = optionalGenre.orElseGet(() -> {
                Genre newGenre = Genre.builder()
                        .name(genreName)
                        .build();
                return genreRepository.save(newGenre);
            });
        }

        return Book.builder()
                .name(bookCreateDto.getName())
                .genre(genre)
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
