package com.silvadiego.libraryapi.Impl;

import com.silvadiego.libraryapi.Exceptions.BusinessException;
import com.silvadiego.libraryapi.Model.Loan;
import com.silvadiego.libraryapi.Repository.LoanRepository;
import com.silvadiego.libraryapi.Service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoanServiceImpl implements LoanService {
    @Autowired
    private LoanRepository repository;

    public LoanServiceImpl(LoanRepository repository) {
        this.repository = repository;
    }

    @Override
    public Loan save(Loan loan) {
        if (repository.existsByBookAndNotReturned(loan.getBook())){
            throw new BusinessException("Book already loaned");

        }
        return repository.save(loan);
    }

    @Override
    public Optional<Loan> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void update(Loan loan) {

    }
}
