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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;

    @Override
    public List<BookDto> getAllBooks() {
        log.info("Getting all books");
        List<Book> books = bookRepository.findAll();
        List<BookDto> bookDtos = books.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
        log.info("Retrieved {} books", bookDtos.size());
        return bookDtos;
    }

    @Override
    public BookDto getByNameV1(String name) {
        log.info("Trying to find book by name (V1) {}", name);
        Optional<Book> book = bookRepository.findBookByName(name);
        if (book.isPresent()) {
            BookDto bookDto = convertEntityToDto(book.get());
            log.info("Found book with name {}: {}", name, bookDto.toString());
            return bookDto;
        } else {
            log.error("Book with name {} not found", name);
            throw new IllegalStateException("Book not found");
        }
    }

    @Override
    public BookDto getByNameV2(String name) {
        log.info("Trying to find book by name (V2) {}", name);
        Optional<Book> book = bookRepository.findBookByNameBySql(name);
        if (book.isPresent()) {
            BookDto bookDto = convertEntityToDto(book.get());
            log.info("Found book with name {}: {}", name, bookDto.toString());
            return bookDto;
        } else {
            log.error("Book with name {} not found", name);
            throw new IllegalStateException("Book not found");
        }
    }

    @Override
    public BookDto getByNameV3(String name) {
        Specification<Book> specification = Specification.where(new Specification<Book>() {
            @Override
            public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("name"), name);
            }
        });

        log.info("Trying to find book by name (V3) {}", name);
        Optional<Book> book = bookRepository.findOne(specification);
        if (book.isPresent()) {
            BookDto bookDto = convertEntityToDto(book.get());
            log.info("Found book with name {}: {}", name, bookDto.toString());
            return bookDto;
        } else {
            log.error("Book with name {} not found", name);
            throw new IllegalStateException("Book not found");
        }
    }

    @Override
    public BookDto createBook(BookCreateDto bookCreateDto) {
        Genre genre = genreRepository.findByName(bookCreateDto.getGenre()
                .getName());
        if (genre == null) {
            genre = new Genre(bookCreateDto.getGenre()
                    .getName());
            genre = genreRepository.save(genre);
        }
        log.info("Creating new book: {}", bookCreateDto.toString());

        Book book = Book.builder()
                .name(bookCreateDto.getName())
                .genre(genre)
                .build();

        book = bookRepository.save(book);
        BookDto bookDto = convertEntityToDto(book);
        log.info("New book created: {}", bookDto.toString());
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

        log.info("Updating book with id {}", bookUpdateDto.getId());
        Optional<Book> book = bookRepository.findById(bookUpdateDto.getId());
        if (book.isPresent()) {
            book.get().setName(bookUpdateDto.getName());
            book.get().setGenre(genre);

            Book savedBook = bookRepository.save(book.get());
            BookDto bookDto = convertEntityToDto(savedBook);
            return bookDto;
        } else {
            log.error("Book with id {} not found", bookUpdateDto.getId());
            throw new IllegalStateException("Book not found");
        }
    }


    @Override
    public void deleteBook(Long id) {
        log.info("Deleting book with id {}", id);
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
