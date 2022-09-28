package com.silvadiego.libraryapi.Service;

import com.silvadiego.libraryapi.Model.Loan;
import org.springframework.stereotype.Service;

@Service
public interface LoanService {
    Loan save(Loan loan);
}
