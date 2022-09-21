package com.silvadiego.libraryapi.Service;

import com.silvadiego.libraryapi.Model.Book;

import java.util.Optional;

public interface BookService {
    Book save(Book any);

    Optional<Book> getById(Long id);

    void delete(Book book);


    Book update(Book book);

}
