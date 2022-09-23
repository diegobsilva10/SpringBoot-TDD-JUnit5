package com.silvadiego.libraryapi.Impl;

import com.silvadiego.libraryapi.Exceptions.BusinessException;
import com.silvadiego.libraryapi.Model.Book;
import com.silvadiego.libraryapi.Repository.BookRepository;
import com.silvadiego.libraryapi.Service.BookService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    final BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override

    public Book save(Book book) {
        if (repository.existsByIsbn(book.getIsbn())){
            throw new BusinessException("ISBN já está cadastrado")


                    ;
        }
        return repository.save(book);
    }

    @Override
    public Optional<Book> getById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public void delete(Book book) throws IllegalAccessException {
        if (book == null || book.getId() == null){
            throw new IllegalAccessException("Book id can't be null");
        }
        this.repository.delete(book);

    }

    @Override
    public Book update (Book book) throws IllegalAccessException {
        if (book == null || book.getId() == null){
            throw new IllegalAccessException("Book id can't be null");
        }
        return this.repository.save(book);

    }
}
