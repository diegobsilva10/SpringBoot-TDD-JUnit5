package com.silvadiego.libraryapi.BookControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.silvadiego.libraryapi.DTO.BookDTO;
import com.silvadiego.libraryapi.Exceptions.BusinessException;
import com.silvadiego.libraryapi.Model.Book;
import com.silvadiego.libraryapi.Service.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("Test")
@WebMvcTest
@AutoConfigureMockMvc
public class ControllerTest {

    static String BOOK_API = "/api/books/";

    @Autowired
    MockMvc mvc; //INJETA DEPENDENCIA

    @MockBean
    BookService service;

    @Test
    @DisplayName("Cria o livro com sucesso!")
    public void createBookTest() throws Exception {

        BookDTO dto = createNewBook();

        Book savedBook = Book.builder().id(10L).author("Autor").title("Titulo ").isbn("123456789").build();

        BDDMockito.given(service.save(Mockito.any(Book.class))).willReturn(savedBook);

        String json = new ObjectMapper().writeValueAsString(dto);

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
                .andExpect(jsonPath("title").value(dto.getTitle()))
                .andExpect(jsonPath("author").value(dto.getAuthor()))
                .andExpect(jsonPath("isbn").value(dto.getIsbn()))

        ;
    }

    private BookDTO createNewBook() {

        return BookDTO.builder().author("Autor").title("Titulo ").isbn("123456789").build();
    }

    @Test
    @DisplayName("Lança erro de validação quando houver dados faltantes!") // DEFINE O QUE ESTÁ SENDO TESTADO
    public void createInvalidBookTest() throws Exception {

        String json = new ObjectMapper().writeValueAsString(new BookDTO());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", hasSize(3)));
    }

    @Test
    @DisplayName("Lança erro ao cadastrar um livro com o isbn já cadastrado")
    public void createBookWithDuplicatedIsbn() throws Exception {

        BookDTO dto = createNewBook();
        String messageError = "ISBN já está cadastrado";


        String json = new ObjectMapper().writeValueAsString(dto);
        BDDMockito.given(service.save(Mockito.any(Book.class)))
                .willThrow(new BusinessException(messageError));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);


        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", hasSize(1)))
                .andExpect(jsonPath("errors[0]").value(messageError));
    }

    @Test
    @DisplayName("Deve obter os dados do livro")
    public void getBookDetailsTeste() throws Exception {

        //Cenário (given)

        Long id = 1L;

        Book book = Book.builder()
                .id(id)
                .title(createNewBook().getTitle())
                .author(createNewBook().getAuthor())
                .isbn(createNewBook().getIsbn())
                .build();
        BDDMockito.given(service.getById(id)).willReturn(Optional.of(book));

        //Execução (when)

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(BOOK_API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(id))
                .andExpect(jsonPath("title").value(createNewBook().getTitle()))
                .andExpect(jsonPath("author").value(createNewBook().getAuthor()))
                .andExpect(jsonPath("isbn").value(createNewBook().getIsbn()))
        ;
    }

    @Test
    @DisplayName("Deve retornar resource not found quando o livro procurado não existir")
    public void NotFoundTest() throws Exception {

        BDDMockito.given(service.getById(anyLong())).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(BOOK_API.concat("/" + 1))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Deve deletar um livro ")
    public void deleteBookTest() throws Exception {
        BDDMockito
                .given(service.getById(anyLong()))
                .willReturn(Optional.of(Book.builder()
                        .id(1L)
                        .build()));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(BOOK_API.concat("/" + 1));

        mvc.perform(request)
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar resource not found quando não encontrar um livro para deletar um livro ")
    public void deleteInexistentBookTest() throws Exception {
        BDDMockito
                .given(service.getById(anyLong())).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(BOOK_API.concat("/" + 1));

        mvc.perform(request)
                .andExpect(status().isNotFound());


    }
}
