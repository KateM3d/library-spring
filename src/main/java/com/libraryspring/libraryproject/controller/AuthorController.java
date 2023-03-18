package com.libraryspring.libraryproject.controller;

import com.libraryspring.libraryproject.dto.AuthorDto;
import com.libraryspring.libraryproject.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/author/{id}")
    AuthorDto getAuthorById(@PathVariable("id") Long id){
        return authorService.getAuthorById(id);
    }
}
