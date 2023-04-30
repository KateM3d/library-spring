package com.libraryspring.libraryproject.dto;

import com.libraryspring.libraryproject.model.Genre;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookCreateDto {
    @Size(min = 3, max = 30)
    @NotBlank(message = "Please add name")
    private String name;

    @Valid
    @NotNull(message = "Please add genre")
    private Genre genre;
}