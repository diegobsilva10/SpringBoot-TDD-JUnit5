package com.silvadiego.libraryapi.Repository;

import com.silvadiego.libraryapi.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByIsbn(String isbn);

    Optional <Book> findByIsbn(String isbn);
}
