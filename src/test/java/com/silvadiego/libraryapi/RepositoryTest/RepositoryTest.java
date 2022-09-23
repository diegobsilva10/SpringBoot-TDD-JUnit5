package com.silvadiego.libraryapi.RepositoryTest;

import com.silvadiego.libraryapi.Model.Book;
import com.silvadiego.libraryapi.Repository.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    BookRepository repository;

    @Test
    @DisplayName("Deve retonar verdadeiro quando existir um livro na base com isbn informado")
    public void returnTrueWhenISBNExists(){
        //cenário
        String isbn = "123456789";
        Book book = createNewBook(isbn);
        entityManager.persist(book);

        //execução
        boolean exists = repository.existsByIsbn(isbn);

        //verificacao
        assertThat(exists).isTrue();
    }


    @Test
    @DisplayName("Deve retonar falso quando não existir um livro na base com isbn informado")
    public void returnFalseWhenISBNDoesntExists(){
        //cenário
        String isbn = "123456789";

        //execução
        boolean exists = repository.existsByIsbn(isbn);

        //verificacao
        assertThat(exists).isFalse();
    }

    public static Book createNewBook(String isbn) {
        return Book.builder()
                .title("Livro Persistencia")
                .author("Fulano")
                .isbn(isbn)
                .build();
    }

    @Test
    @DisplayName("Deve obter um livro por ID")
    public void findByIdTest (){
        //cenário

       Book book =  createNewBook("1235");
       entityManager.persist(book);

       //execução
       Optional<Book> foundBook =  repository.findById(book.getId());

       //verificação
        assertThat(foundBook.isPresent()).isTrue();

    }
    @Test
    @DisplayName("Deve atualizar um livro")
    public void saveBookTest(){
        //cenário
        Book book = createNewBook("123");

        Book savedBook =  repository.save(book);

        assertThat(savedBook.getId()).isNotNull();
    }

    @Test
    @DisplayName("Deve deletar um livro")
    public void deleteBookTest(){
        //cenário
        String isbn = "1234";
        Book book = createNewBook(isbn);
        entityManager.persist(book);

        Book foundBook =  entityManager.find(Book.class, book.getId());

        repository.delete(foundBook);

        Book deletedBook = entityManager.find(Book.class, book.getId());
        assertThat(deletedBook).isNull();

    }
}
