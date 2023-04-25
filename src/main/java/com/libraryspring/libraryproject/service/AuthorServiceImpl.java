package com.libraryspring.libraryproject.service;

import com.libraryspring.libraryproject.dto.AuthorCreateDto;
import com.libraryspring.libraryproject.dto.AuthorDto;
import com.libraryspring.libraryproject.dto.AuthorUpdateDto;
import com.libraryspring.libraryproject.dto.BookDto;
import com.libraryspring.libraryproject.model.Author;
import com.libraryspring.libraryproject.repository.AuthorRepository;
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
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public AuthorDto getAuthorById(Long id) {
        log.info("Trying to find author by id {}", id);
        Optional<Author> author = authorRepository.findById(id);
        if(author.isPresent()){
            AuthorDto authorDto = convertEntityToDto(author.get());
            log.info("Found author with id: {}", authorDto.toString());
            return authorDto;
        }else{
            log.error("Author with id {} not found", id);
            throw new IllegalStateException("Author not found");
        }
    }

    @Override
    public AuthorDto getByNameV1(String name) {
        log.info("Trying to find author by name (V1) {}", name);
        Optional<Author> author = authorRepository.findAuthorByName(name);
        if(author.isPresent()) {
            AuthorDto authorDto = convertEntityToDto(author.get());
            log.info("Found author with name {}: {}", name, authorDto.toString());
            return authorDto;
        }
        else {
            log.error("Author with name {} not found", name);
            throw new IllegalStateException("Author not found");
        }
    }

    @Override
    public AuthorDto getByNameV2(String name) {
        log.info("Trying to find author by name (V2) {}", name);
        Optional<Author> author = authorRepository.findAuthorByNameBySql(name);
        if(author.isPresent()) {
            AuthorDto authorDto = convertEntityToDto(author.get());
            log.info("Found author with name {}: {}", name, authorDto.toString());
            return authorDto;
        }
        else {
            log.error("Author with name {} not found", name);
            throw new IllegalStateException("Author not found");
        }
    }

    @Override
    public AuthorDto getByNameV3(String name) {
        Specification<Author> specification = Specification.where(new Specification<Author>() {
            @Override
            public Predicate toPredicate(Root<Author> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("name"), name);
            }
        });
        log.info("Trying to find author by name (V3) {}", name);
        Optional<Author> author = authorRepository.findOne(specification);
        if(author.isPresent()) {
            AuthorDto authorDto = convertEntityToDto(author.get());
            log.info("Found author with name {}: {}", name, authorDto.toString());
            return authorDto;
        }
        else {
            log.error("Author with name {} not found", name);
            throw new IllegalStateException("Author not found");
        }
    }

    @Override
    public AuthorDto createAuthor(AuthorCreateDto authorCreateDto) {
        log.info("Creating new author: {}", authorCreateDto.toString());
        Author author = authorRepository.save(convertDtoToEntity(authorCreateDto));
        AuthorDto authorDto = convertEntityToDto(author);
        log.info("New author created: {}", authorDto.toString());
        return authorDto;
    }

    @Override
    public AuthorDto updateAuthor(AuthorUpdateDto authorUpdateDto) {
        log.info("Updating author with id {}", authorUpdateDto.getId());
        Optional<Author> authorOptional = authorRepository.findById(authorUpdateDto.getId());
        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();
            author.setName(authorUpdateDto.getName());
            author.setSurname(authorUpdateDto.getSurname());
            Author savedAuthor = authorRepository.save(author);
            AuthorDto authorDto = convertEntityToDto(savedAuthor);
            log.info("Updated author with id {}", authorUpdateDto.getId());
            return authorDto;
        } else {
            log.error("Author with id {} not found", authorUpdateDto.getId());
            throw new IllegalStateException("Author not found");
        }
    }

    @Override
    public void deleteAuthor(Long id) {
        log.info("Deleting author with id {}", id);
        authorRepository.deleteById(id);
    }

    @Override
    public List<AuthorDto> getAllAuthors() {
        log.info("Getting all authors");
        List<Author> authors = authorRepository.findAll();
        return authors.stream()
                .map(author -> AuthorDto.builder()
                        .id(author.getId())
                        .name(author.getName())
                        .surname(author.getSurname())
                        .books(author.getBooks()
                                .stream()
                                .map(book -> BookDto.builder()
                                        .id(book.getId())
                                        .name(book.getName())
                                        .genre(book.getGenre()
                                                .getName())
                                        .build())
                                .collect(Collectors.toList()))

                        .build())
                .collect(Collectors.toList());
    }

    private Author convertDtoToEntity(AuthorCreateDto authorCreateDto) {
        return Author.builder()
                .name(authorCreateDto.getName())
                .surname(authorCreateDto.getSurname())
                .build();
    }

    private AuthorDto convertEntityToDto(Author author) {

        List<BookDto> bookDtoList = null;
        if (author.getBooks() != null) {
            bookDtoList = author.getBooks()
                    .stream()
                    .map(book -> BookDto.builder()
                            .genre(book.getGenre()
                                    .getName())
                            .name(book.getName())
                            .id(book.getId())
                            .build())
                    .toList();
        }

        AuthorDto authorDto = AuthorDto.builder()
                .id(author.getId())
                .name(author.getName())
                .surname(author.getSurname())
                .books(bookDtoList)
                .build();
        return authorDto;
    }


}
