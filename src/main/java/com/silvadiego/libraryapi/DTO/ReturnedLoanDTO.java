package com.silvadiego.libraryapi.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReturnedLoanDTO {
    private boolean returned;

}
