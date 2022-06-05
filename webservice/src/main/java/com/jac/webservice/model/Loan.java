package com.jac.webservice.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Loan {
    private int id;
    private int customerId;
    private int bookId;
    private LocalDate dateOut;
    private LocalDate dateIn;

    public Loan(int customerId, int bookId, LocalDate dateOut) {
        this.customerId = customerId;
        this.bookId = bookId;
        this.dateOut = dateOut;
    }

    public Loan(int id, LocalDate dateIn) {
        this.id = id;
        this.dateIn = dateIn;
    }
}
