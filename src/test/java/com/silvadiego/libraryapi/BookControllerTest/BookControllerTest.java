package com.silvadiego.libraryapi.BookControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class BookControllerTest {

    static String BOOK_API = "/api/books";

    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("Cria um livro com sucesso")
    public void createBookTest () throws Exception{

        String json = new ObjectMapper().writeValueAsString(null);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("json");

        mvc
                .perform(request)
                .andExpect(status().isCreated() )
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("title").value("Meu livro"))
                .andExpect(jsonPath("Author").value("Author 1"))
                .andExpect(jsonPath("isbn").value("123456789"))

                ;
    }

    @Test
    @DisplayName("Lana erro de validação quando não houver dados suficientes para a criação de um livro")
    public void createInvalidBookTest (){
    }

}
