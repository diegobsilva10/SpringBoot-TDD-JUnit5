package com.silvadiego.libraryapi.Repository;

import com.silvadiego.libraryapi.Model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
