package com.jac.webservice.service;

import com.jac.webservice.model.Loan;
import com.jac.webservice.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {

    @Autowired
    LoanRepository repository;

    //    get all loans
    public List<Loan> getAllLoans() {
        return repository.getAllLoans();
    }

    //    get loan by id
    public Loan getLoanById(int id) {
            return repository.getLoanById(id);
    }

    //    get loan by customer id
    public List<Loan> getLoansByCustomerId(int customerId) {
            return repository.getLoanListByCustomerId(customerId);
    }

//    get loan by book id
    public List<Loan> getLoansByBookId(int bookId) {
            return repository.getLoanListByBookId(bookId);
    }

    //    get loans by book isbn
    public List<Loan> getLoansByBookISBN(String isbn) {
        return repository.getLoanListByBookISBN(isbn);
    }

//    get last loan by a customer
    public Loan getLastLoanByCustomerId(int customerId) {
            return repository.getLastLoanByCustomerId(customerId);
    }

//    get last loan of a book
public Loan getLastLoanByBookId(int bookId) {
        return repository.getLastLoanByBookId(bookId);
}

    //    update loan by id
    public Loan updateLoanById(int id, Loan loan) throws IllegalAccessException {
        return repository.updateLoanById(id, loan);
    }

    //    update a loan's return date'
    public Loan updateLoanDateIn(int id, LocalDate dateIn) {
        return repository.updateLoanDateIn(id, dateIn);
    }

    //    save a new loan
    public Loan saveLoan(Loan loan) {
        return repository.saveLoan(loan);

    }

    //    delete a loan
    public void deleteLoanById(int id) {
        repository.deleteLoanById(id);
    }

}
