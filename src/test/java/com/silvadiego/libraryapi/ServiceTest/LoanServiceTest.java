package com.silvadiego.libraryapi.ServiceTest;

import com.silvadiego.libraryapi.Impl.LoanServiceImpl;
import com.silvadiego.libraryapi.Model.Book;
import com.silvadiego.libraryapi.Model.Loan;
import com.silvadiego.libraryapi.Repository.LoanRepository;
import com.silvadiego.libraryapi.Service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("teste")
public class LoanServiceTest {

    LoanService loanService;
    @MockBean
    LoanRepository repository;

    @BeforeEach
    public void setUp(){
        this.loanService = new LoanServiceImpl(repository);
    }

    @Test
    @DisplayName("Deve salvar empréstimo")
    public void saveLoanTest(){

        Book book = Book.builder().id(1L).build();
        String customer = "Fulano";
        //cenário

        Loan savingLoan = Loan.builder()
                .book(Book.builder().id(1L).build())
                .customerLoan(customer)
                .loanDate(LocalDate.now())
                .build();

        Loan savedLoan = Loan.builder()
                .idLoan(1L)
                .loanDate(LocalDate.now())
                .customerLoan(customer)
                .book(book)
                .build();

        when(repository.save(savingLoan)).thenReturn(savedLoan);

        Loan Loan = loanService.save(savingLoan);

        assertThat(Loan.getIdLoan()).isEqualTo(savedLoan.getIdLoan());
        assertThat(Loan.getBook().getId()).isEqualTo(savedLoan.getBook().getId());
        assertThat(Loan.getCustomerLoan()).isEqualTo(savedLoan.getCustomerLoan());
        assertThat(Loan.getIdLoan()).isEqualTo(savedLoan.getIdLoan());
        assertThat(Loan.getLoanDate()).isEqualTo(savedLoan.getLoanDate());

    }
}
