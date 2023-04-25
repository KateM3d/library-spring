package com.libraryspring.libraryproject.dto;

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
public class AuthorUpdateDto {
    private Long id;
    @Size(min = 3,max = 30)
    @NotBlank(message = "Please add name")
    private String name;
    @NotBlank(message = "Please add surname")
    private String surname;
}
