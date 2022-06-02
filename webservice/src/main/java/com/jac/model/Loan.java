package com.jac.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Loan {
    private int customerId;
    private int bookId;
    private LocalDate dateOut;
    private LocalDate dateIn;

}
