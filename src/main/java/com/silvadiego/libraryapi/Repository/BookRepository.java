package com.silvadiego.libraryapi.Repository;

import com.silvadiego.libraryapi.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
