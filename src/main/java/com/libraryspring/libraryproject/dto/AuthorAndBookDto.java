package com.libraryspring.libraryproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthorAndBookDto {
    private Long id;
    private String name;
    private String author;
}
