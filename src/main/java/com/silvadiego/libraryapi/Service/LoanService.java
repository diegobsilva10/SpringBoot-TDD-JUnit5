package com.silvadiego.libraryapi.Service;

import com.silvadiego.libraryapi.Model.Loan;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface LoanService {
    Loan save(Loan loan);

    Optional<Loan> getById(Long id);

    Loan update(Loan loan);

}
