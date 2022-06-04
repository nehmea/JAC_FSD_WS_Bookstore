package com.jac.webservice.model;

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

    public Loan(int customerId, int bookId, LocalDate dateOut) {
        this.customerId = customerId;
        this.bookId = bookId;
        this.dateOut = dateOut;
    }
}
