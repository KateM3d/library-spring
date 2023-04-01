package com.libraryspring.libraryproject.service;
import com.libraryspring.libraryproject.dto.GenreBookWithAuthorDto;
import com.libraryspring.libraryproject.dto.GenreDto;

import java.util.List;

public interface GenreService {
    GenreBookWithAuthorDto getGenreById(Long id);

    List<GenreDto> getAllGenres();
}