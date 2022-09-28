package com.silvadiego.libraryapi.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Locale;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLoan;

    @Column
    private String customerLoan;

    @JoinColumn
    @ManyToOne
    private Book book;

    @Column
    private LocalDate loanDate;

    @Column
    private Boolean returned;
}
