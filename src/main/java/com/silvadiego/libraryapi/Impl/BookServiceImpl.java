package com.silvadiego.libraryapi.Impl;

import com.silvadiego.libraryapi.Exceptions.BusinessException;
import com.silvadiego.libraryapi.Model.Book;
import com.silvadiego.libraryapi.Repository.BookRepository;
import com.silvadiego.libraryapi.Service.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override

    public Book save(Book book) {
        if (repository.existsByISBN(book.getIsbn())){
            throw new BusinessException("ISBN já está cadastrado")


                    ;
        }
        return repository.save(book);
    }
}
