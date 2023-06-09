package com.libraryspring.libraryproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libraryspring.libraryproject.dto.AuthorDto;
import com.libraryspring.libraryproject.dto.BookCreateDto;
import com.libraryspring.libraryproject.dto.BookDto;
import com.libraryspring.libraryproject.dto.BookUpdateDto;
import com.libraryspring.libraryproject.model.Genre;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc(addFilters = false)
public class BookRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateBook() throws Exception {
        String name = "Beauty and the Beast";
        Genre genre = new Genre("Story");

        BookCreateDto bookCreateDto = BookCreateDto.builder()
                .name(name)
                .genre(genre)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/book/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookCreateDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name")
                        .value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre")
                        .value(genre.getName()));
    }


    @Test
    public void testUpdateBook() throws Exception {
        Long id = 18L;
        String name = "Harry Potter and Azkaban";
        String genre = "Story";

        BookUpdateDto bookUpdateDto = BookUpdateDto.builder()
                .id(id)
                .name(name)
                .genre(genre)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put("/book/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookUpdateDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name")
                        .value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre")
                        .value(genre));
    }

    @Test
    public void testDeleteBook() throws Exception {
        Long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/book/delete/{id}", id))
                .andExpect(status().isOk());

    }

}
