package com.libraryspring.libraryproject.repository;

import com.libraryspring.libraryproject.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
