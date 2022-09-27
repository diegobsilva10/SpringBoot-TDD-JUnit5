package com.silvadiego.libraryapi.BookControllerTest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.silvadiego.libraryapi.Controller.LoanController;
import com.silvadiego.libraryapi.DTO.LoanDTO;
import com.silvadiego.libraryapi.Model.Book;
import com.silvadiego.libraryapi.Model.Loan;
import com.silvadiego.libraryapi.Service.BookService;
import com.silvadiego.libraryapi.Service.LoanService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
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

import java.time.LocalDate;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("Test")
@AutoConfigureMockMvc
@WebMvcTest(controllers = {LoanController.class})
public class LoanControllerTest {
    @Autowired
    MockMvc mvc;
    static final String LOAN_API = "/api/loans";
    @MockBean
    private BookService bookService;
    @MockBean
    private LoanService loanService;

    @Test
    @DisplayName("Deve realizar um empréstimo")
    public void createLoanTest() throws Exception{


        LoanDTO dto =   LoanDTO.builder().isbn("123").customer("Diego").build();
        String json = new ObjectMapper().writeValueAsString(dto);

        Book createBook = Book.builder().id(1L).isbn("123").build();

        BDDMockito.given(bookService.getBookByIsbn("123"))
                .willReturn(Optional.of(createBook));


        Loan loan = Loan.builder().idLoan(1L).customerLoan("Diego").book(createBook).loanDate(LocalDate.now()).build();
        BDDMockito.given(loanService.save(Mockito.any(Loan.class))).willReturn(loan);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(LOAN_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect( status().isCreated())
                .andExpect(content().string("1"));
    }


}