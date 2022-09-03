package com.silvadiego.libraryapi.ServiceTest;

import com.silvadiego.libraryapi.Model.Book;
import com.silvadiego.libraryapi.Service.BookService;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {

    BookService service;

    @Test
    @DisplayName("Deve salvar um livro")
    public void saveBookTest(){
        //Cenário

        Book book = Book.builder().isbn("123456")
                .author("2 author")
                .title("Livro 2")
                .build();

        //Execução do teste
       Book savedBook = service.save(book);

       //verificação

        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getIsbn()).isEqualTo("123456");
        assertThat(savedBook.getTitle()).isEqualTo("Livro 2");
        assertThat(savedBook.getAuthor()).isEqualTo("2 author");
    }
}
