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

@ExtendWith(SpringExtension.class) //CRIA UM MINI CONTEXTO PARA RODAR O TESTE, COM AS CLASSES DEFINIDAS
@ActiveProfiles("Test")
@WebMvcTest
@AutoConfigureMockMvc // CONFIGURA OS OBJETOS TESTE PARA FAZER AS REQUISIÇÕES
public class ControllerTest {

    static String BOOK_API = "/api/books";

    @Autowired
    MockMvc mvc; //INJETA DEPENDENCIA

    @Test
    @DisplayName("Cria o livro com sucesso!") // DEFINE O QUE ESTÁ SENDO TESTADO
    public void createBookTest () throws Exception{

        String json = new ObjectMapper().writeValueAsString(null);

        //DEFINE A REQUISIÇÃO
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("title").value("Meu Livro"))
                .andExpect(jsonPath("author").value("Autor"))
                .andExpect(jsonPath("isbn").value("123456789"))

                ;
    }

    @Test
    @DisplayName("Lança erro de validação quando houver dados faltantes!") // DEFINE O QUE ESTÁ SENDO TESTADO
    public void createInvalidBookTest (){

    }
}
