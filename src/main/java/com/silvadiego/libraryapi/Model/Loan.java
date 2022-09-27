package com.silvadiego.libraryapi.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Locale;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Loan {

    private Long idLoan;
    private String customerLoan;
    private Book book;
    private LocalDate loanDate;
    private Boolean returned;
}
