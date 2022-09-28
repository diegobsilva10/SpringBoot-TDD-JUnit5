package com.silvadiego.libraryapi.DTO;

import com.silvadiego.libraryapi.Model.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanDTO {
    private Long id;
    private String customer;
    private String isbn;
    private BookDTO book;

}
