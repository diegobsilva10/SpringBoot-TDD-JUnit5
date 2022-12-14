package com.silvadiego.libraryapi.Repository;

import com.silvadiego.libraryapi.Model.Book;
import com.silvadiego.libraryapi.Model.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    @Query("select case when (count(l.id) > 0) then true else false end " +
            "from Loan l " +
            "where l.book = :book and (l.returned is null or l.returned is false)")
    boolean existsByBookAndNotReturned(@Param("book") Book book);

    Page<Loan> findByBook(Book book, Pageable pageable);



    @Query("select l from Loan as l join l.book as b where b.isbn =:isbn or l.customerLoan =:customerLoan")
    Page<Loan> findByBookIsbnOrCustomer(@Param("isbn") String isbn,@Param("customerLoan") String customer, Pageable pageable);
}
