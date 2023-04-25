package com.libraryspring.libraryproject.controller.rest;

import com.libraryspring.libraryproject.dto.GenreBookWithAuthorDto;
import com.libraryspring.libraryproject.dto.GenreDto;
import com.libraryspring.libraryproject.service.GenreService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "library-users")
public class GenreRestController {
    private final GenreService genreService;
    @GetMapping("/genres")
    public List<GenreDto> getAllGenres() {
        return genreService.getAllGenres();
    }
    @GetMapping("/genre/{id}")
    public GenreBookWithAuthorDto getGenreById(@PathVariable("id") Long id) {
        return genreService.getGenreById(id);
    }
}