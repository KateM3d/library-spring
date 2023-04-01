package com.libraryspring.libraryproject.service;
import com.libraryspring.libraryproject.dto.GenreDto;

public interface GenreService {
    GenreDto getGenreById(Long id);
}