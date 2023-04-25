package com.libraryspring.libraryproject.dto;

import com.libraryspring.libraryproject.model.Genre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @Size(min = 3, max=30)
    @NotBlank(message = "please enter name")
    private String name;
    @NotBlank(message = "please enter genre")
    private String genre;
}
