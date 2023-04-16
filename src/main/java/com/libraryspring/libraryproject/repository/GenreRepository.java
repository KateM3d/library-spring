package com.libraryspring.libraryproject.repository;

import com.libraryspring.libraryproject.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Genre findByName(String name);

}
