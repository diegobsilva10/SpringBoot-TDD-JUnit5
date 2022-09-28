package com.silvadiego.libraryapi.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoanFilterDTO {

    private String isbn;
    private String customer;

}