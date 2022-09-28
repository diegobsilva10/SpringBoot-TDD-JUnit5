package com.silvadiego.libraryapi.RepositoryTest;

import com.silvadiego.libraryapi.Model.Book;
import com.silvadiego.libraryapi.Model.Loan;
import com.silvadiego.libraryapi.Repository.LoanRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LoanRepositoryTest {

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("deve verificar se existe empréstimo não devolvido para o livro")
    public void existsByBookAndNotReturnedTest (){

        //Cenário

        Book book = createNewBook("123");
        entityManager.persist(book);

        Loan loan = Loan.builder()
                .book(book)
                .customerLoan("Fulano")
                .loanDate(LocalDate.now())
                .build();
        entityManager.persist(loan);

        boolean exists = loanRepository.existsByBookAndNotReturned(book);

        assertThat(exists).isTrue();

    }

    @Test
    @DisplayName("Deve buscar emprestimo pelo isbn do livro ou customer")
    public void findBookIsbnOrCustomerTest() {
        Loan loan = createAndPersistLoan(LocalDate.now());

        Page<Loan> result = loanRepository.findByBookIsbnOrCustomer("123", "Fulano", PageRequest.of(0, 10));


        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent()).contains(loan);
        assertThat(result.getPageable().getPageSize()).isEqualTo(10);
        assertThat(result.getPageable().getPageNumber()).isEqualTo(0);
        assertThat(result.getTotalElements()).isEqualTo(1);
    }

    public static Book createNewBook(String isbn) {
        return Book.builder()
                .title("Livro Persistencia")
                .author("Fulano")
                .isbn(isbn)
                .build();
    }

    private Loan createAndPersistLoan(LocalDate loanDate) {
        Book book = createNewBook("123");
        entityManager.persist(book);

        Loan loan = Loan.builder()
                .book(book)
                .customerLoan("Fulano")
                .loanDate(loanDate)
                .build();

        entityManager.persist(loan);

        return loan;
    }

}
