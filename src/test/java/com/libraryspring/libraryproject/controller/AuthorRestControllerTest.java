package com.libraryspring.libraryproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libraryspring.libraryproject.dto.AuthorDto;
import com.libraryspring.libraryproject.dto.BookDto;
import com.libraryspring.libraryproject.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class AuthorRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthorService authorService;

    @Test
    public void testGetAuthorById() throws Exception {
        Long id = 1L;
        String name = "Alexander";
        String surname = "Pushkin";

        AuthorDto authorDto = AuthorDto.builder()
                .id(id)
                .name(name)
                .surname(surname)
                .build();

        Mockito.when(authorService.getAuthorById(id)).thenReturn(authorDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/author/{id}", id))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(authorDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value(authorDto.getSurname()));
    }

    @Test
    public void testGetAuthorByNameV1() throws Exception {
        String name = "Alexander";
        String surname = "Pushkin";
        List<BookDto> books = new ArrayList<>();

        AuthorDto authorDto = AuthorDto.builder()
                .name(name)
                .surname(surname)
                .books(books)
                .build();

        Mockito.when(authorService.getByNameV1(name)).thenReturn(authorDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/author/v1?name={name}", name))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value(authorDto.getSurname()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books").value(authorDto.getBooks()));
    }

    @Test
    public void testGetAuthorByNameV2() throws Exception {
        String name = "Alexander";
        String surname = "Pushkin";
        List<BookDto> books = new ArrayList<>();

        AuthorDto authorDto = AuthorDto.builder()
                .name(name)
                .surname(surname)
                .books(books)
                .build();

        Mockito.when(authorService.getByNameV2(name)).thenReturn(authorDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/author/v2?name={name}", name))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value(authorDto.getSurname()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books").value(authorDto.getBooks()));
    }

    @Test
    public void testGetAuthorByNameV3() throws Exception {
        String name = "Alexander";
        String surname = "Pushkin";
        List<BookDto> books = new ArrayList<>();

        AuthorDto authorDto = AuthorDto.builder()
                .name(name)
                .surname(surname)
                .books(books)
                .build();

        Mockito.when(authorService.getByNameV3(name)).thenReturn(authorDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/author/v3?name={name}", name))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value(authorDto.getSurname()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books").value(authorDto.getBooks()));
    }

    @Test
    public void testCreateAuthor() throws Exception {
        String name = "John";
        String surname = "Doest";

        AuthorDto authorCreateDto = AuthorDto.builder()
                .name(name)
                .surname(surname)
                .build();

        Mockito.when(authorService.createAuthor(Mockito.any())).thenReturn(authorCreateDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/author/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorCreateDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value(surname));
    }



    @Test
    public void testUpdateAuthor() throws Exception {
        Long id = 1L;
        String name = "Johnes";
        String surname = "Does";

        AuthorDto authorUpdateDto = AuthorDto.builder()
                .id(id)
                .name(name)
                .surname(surname)
                .build();

        Mockito.when(authorService.updateAuthor(Mockito.any())).thenReturn(authorUpdateDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/author/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorUpdateDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value(surname));
    }

    @Test
    public void testDeleteAuthor() throws Exception {
        Long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/author/delete/{id}", id))
                .andExpect(status().isOk());

    }
}
