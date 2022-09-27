package com.silvadiego.libraryapi.Controller;

import com.silvadiego.libraryapi.DTO.LoanDTO;
import com.silvadiego.libraryapi.Model.Book;
import com.silvadiego.libraryapi.Model.Loan;
import com.silvadiego.libraryapi.Service.BookService;
import com.silvadiego.libraryapi.Service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;
    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody LoanDTO dto){
        Book book = bookService.getBookByIsbn(dto.getIsbn())
                .orElseThrow(()->
                        new ResponseStatusException( HttpStatus.BAD_REQUEST,"Book not found for passed isbn"));

        Loan entity = Loan.builder()
                .book(book)
                .customerLoan(dto.getCustomer())
                .loanDate(LocalDate.now())
                .build();
        entity = loanService.save(entity);

        return entity.getIdLoan();

    }
}
