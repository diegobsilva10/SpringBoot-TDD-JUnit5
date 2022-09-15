package com.silvadiego.libraryapi.ServiceTest;

import com.silvadiego.libraryapi.Exceptions.BusinessException;
import com.silvadiego.libraryapi.Impl.BookServiceImpl;
import com.silvadiego.libraryapi.Model.Book;
import com.silvadiego.libraryapi.Repository.BookRepository;
import com.silvadiego.libraryapi.Service.BookService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {

    BookService service;
    @MockBean
    BookRepository repository;

    //Essa anotação faz com que o método seja executado antes de cada teste
    @BeforeEach
    public void setUp (){
        this.service = new BookServiceImpl(repository);
    }

    @Test
    @DisplayName("Deve salvar um livro")
    public void saveBookTest(){
        //Cenário

        Book book = getBook();
        Mockito.when(repository.existsByISBN(Mockito.anyString()))
                .thenReturn(false);
        Mockito.when(repository.save(book)).thenReturn (
                 Book.builder()
                         .id(1L)
                         .isbn("123456")
                         .author("2 author")
                         .title("Livro 2")

                .build());

        //Execução do teste
       Book savedBook = service.save(book);

       //verificação

        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getIsbn()).isEqualTo("123456");
        assertThat(savedBook.getTitle()).isEqualTo("Livro 2");
        assertThat(savedBook.getAuthor()).isEqualTo("2 author");
    }

    private static Book getBook() {
        return Book.builder().isbn("123456")
                .author("2 author")
                .title("Livro 2")
                .build();
    }

    @Test
    @DisplayName("Deve lançar erro de negócio ao tantar salvar livro com o mesmo ISBN")
    public void shouldNotSaveABookWithDuplicatedISBN(){
        //cenario
        Book book = getBook();
        Mockito.when(repository.existsByISBN(Mockito.anyString()))
                .thenReturn(true)
        ;

        //execução
        Throwable exception = Assertions.catchThrowable(() -> service.save(book));

        //verificação
        assertThat(exception)
                .isInstanceOf(BusinessException.class)
                .hasMessage("ISBN já está cadastrado");

        //verifica que o repository nunca vai executar o método save com esse parametro book
        Mockito.verify(repository, Mockito.never()).save(book);
    }


}