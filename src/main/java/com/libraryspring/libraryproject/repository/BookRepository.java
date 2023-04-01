package com.libraryspring.libraryproject.repository;

import com.libraryspring.libraryproject.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface BookRepository extends JpaRepository<Book, Long> {
}
