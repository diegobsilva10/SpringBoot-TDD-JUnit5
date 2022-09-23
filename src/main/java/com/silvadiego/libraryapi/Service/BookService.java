package com.silvadiego.libraryapi.Service;

import com.silvadiego.libraryapi.Model.Book;
import com.silvadiego.libraryapi.Repository.BookRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface BookService {

    Book save(Book any);

    Optional<Book> getById(Long id);

    void delete(Book book) throws IllegalAccessException;


    Book update(Book book) throws IllegalAccessException;

}
