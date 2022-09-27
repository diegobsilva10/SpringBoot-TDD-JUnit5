package com.silvadiego.libraryapi.Impl;

import com.silvadiego.libraryapi.Exceptions.BusinessException;
import com.silvadiego.libraryapi.Model.Loan;
import com.silvadiego.libraryapi.Repository.LoanRepository;
import com.silvadiego.libraryapi.Service.LoanService;

public class LoanServiceImpl implements LoanService {
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
}
