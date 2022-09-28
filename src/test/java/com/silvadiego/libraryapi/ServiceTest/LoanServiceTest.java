package com.silvadiego.libraryapi.ServiceTest;

import com.silvadiego.libraryapi.DTO.LoanFilterDTO;
import com.silvadiego.libraryapi.Exceptions.BusinessException;
import com.silvadiego.libraryapi.Impl.LoanServiceImpl;
import com.silvadiego.libraryapi.Model.Book;
import com.silvadiego.libraryapi.Model.Loan;
import com.silvadiego.libraryapi.Repository.LoanRepository;
import com.silvadiego.libraryapi.Service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

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

        when(repository.existsByBookAndNotReturned(book)).thenReturn(false);
        when(repository.save(savingLoan)).thenReturn(savedLoan);

        Loan Loan = loanService.save(savingLoan);

        assertThat(Loan.getIdLoan()).isEqualTo(savedLoan.getIdLoan());
        assertThat(Loan.getBook().getId()).isEqualTo(savedLoan.getBook().getId());
        assertThat(Loan.getCustomerLoan()).isEqualTo(savedLoan.getCustomerLoan());
        assertThat(Loan.getIdLoan()).isEqualTo(savedLoan.getIdLoan());
        assertThat(Loan.getLoanDate()).isEqualTo(savedLoan.getLoanDate());

    }

    @Test
    @DisplayName("Lança erro de negócio ao salvar empréstimo com livro já emprestado")
    public void loanedBookSaveTest(){

        Book book = Book.builder().id(1L).build();
        String customer = "Fulano";
        //cenário

        Loan savingLoan = Loan.builder()
                .book(Book.builder().id(1L).build())
                .customerLoan(customer)
                .loanDate(LocalDate.now())
                .build();

        when(repository.existsByBookAndNotReturned(book)).thenReturn(true);


      Throwable exception =  catchThrowable(()-> loanService.save(savingLoan));

      assertThat(exception).isInstanceOf(BusinessException.class)
              .hasMessage("Book already loaned");

      verify(repository, never ()).save (savingLoan);
    }

    @Test
    @DisplayName("Deve obter as informações do empréstimo pelo id")
    public void getLoanDetailsTest(){
        Long id = 1L;

        Book book = createNewBook("1234");
        Loan loan = createNewLoan(createNewBook("1234"));
        loan.setIdLoan(id);

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(loan));

        Optional<Loan> result = loanService.getById(id);

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getIdLoan()).isEqualTo(id);
        assertThat(result.get().getCustomerLoan()).isEqualTo(loan.getCustomerLoan());
        assertThat(result.get().getLoanDate()).isEqualTo(loan.getLoanDate());
        assertThat(result.get().getBook()).isEqualTo(loan.getBook());

        verify(repository).findById(id);

    }

    @Test
    @DisplayName("Deve atualizar um empréstimo")
    public void updateLoanTest(){
        Book book = createNewBook("123");
        Loan loan = createNewLoan(createNewBook("1234"));
        loan.setIdLoan(1L);
        loan.setReturned(true);

        when(repository.save(loan)).thenReturn(loan);


        Loan updatedLoan = loanService.update(loan);

        assertThat(updatedLoan.getReturned()).isTrue();

        verify(repository).save(loan);
    }

    public static Book createNewBook(String isbn) {
        return Book.builder()
                .title("Livro Persistencia")
                .author("Fulano")
                .isbn(isbn)
                .build();
    }

    @Test
    @DisplayName("Deve filtrar os empréstimos pelas propriedades")
    public void findBookTest() {

        //cenario
        Loan loan = createNewLoan(createNewBook("1234"));
        loan.setIdLoan(1L);

        LoanFilterDTO dto = LoanFilterDTO.builder().customer("Fulano").isbn("123").build();

        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Loan> lista = List.of(loan);
        Page<Loan> page = new PageImpl<Loan>(lista, pageRequest, 1);
        when(repository.findByBookIsbnOrCustomer(Mockito.anyString(), Mockito.anyString(),Mockito.any(Pageable.class)))
                .thenReturn(page);

        //execucao
        Page<Loan> result = loanService.find(dto, pageRequest);

        //verificacoes
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent()).isEqualTo(lista);
        assertThat(result.getPageable().getPageSize()).isEqualTo(10);
        assertThat(result.getPageable().getPageNumber()).isEqualTo(0);
    }

    public Loan createNewLoan(Book newBook) {
       Book book = Book.builder().id(1L).build();
        String customer = "customer";
        Loan savingLoan = Loan.builder()
                .book(book)
                .customerLoan(customer)
                .loanDate(LocalDate.now())
                .build();
        return savingLoan;
    }
}
