package com.libraryspring.libraryproject.dto;

import com.libraryspring.libraryproject.model.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BookUpdateDto {
    private Long id;
    private String name;
    private String genre;
}
