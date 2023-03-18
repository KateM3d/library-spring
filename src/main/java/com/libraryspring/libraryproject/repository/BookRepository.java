package com.libraryspring.libraryproject.repository;

import com.libraryspring.libraryproject.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
