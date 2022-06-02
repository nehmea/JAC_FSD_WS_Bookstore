package com.jac.restservice;

import com.jac.exceptions.DatabaseException;
import com.jac.exceptions.ItemExistException;
import com.jac.exceptions.RecordDoesNotExistInDatabaseException;
import com.jac.model.Loan;
import com.jac.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    public LoanService service;

    @GetMapping("")
    public ResponseEntity<List<Loan>> getAllLoans() {
        try {
            return new ResponseEntity<>(service.getAllLoans(), HttpStatus.OK);
        }
        catch (DataRetrievalFailureException exception) {
            return new ResponseEntity(exception.getMessage(), HttpStatus.OK);
        }
    }

    @GetMapping("/loan/id/{id}")
    public ResponseEntity<Loan> getLoanById(@PathVariable(value = "id") int id) {
        try{
            return new ResponseEntity<>(service.getLoanById(id), HttpStatus.OK);
        }
        catch (DataRetrievalFailureException exception){
            return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/customer/id/{id}")
    public ResponseEntity<List<Loan>> getLoansByCustomerId(@PathVariable(value = "id") int id) {
        try{
            return new ResponseEntity<>(service.getLoansByCustomerId(id), HttpStatus.OK);
        }
        catch (DataRetrievalFailureException exception){
            return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/book/id/{id}")
    public ResponseEntity<List<Loan>> getLoansByBookId(@PathVariable(value = "id") int id) {
        try{
            return new ResponseEntity<>(service.getLoansByBookId(id), HttpStatus.OK);
        }
        catch (DataRetrievalFailureException exception){
            return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/book/isbn/{isbn}")
    public ResponseEntity<List<Loan>> getLoansByBookISBN(@PathVariable(value = "isbn") String isbn) {
        try{
            return new ResponseEntity<>(service.getLoansByBookISBN(isbn), HttpStatus.OK);
        }
        catch (DataRetrievalFailureException exception){
            return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/customer/{id}/last")
    public ResponseEntity<Loan> getLastLoanByCustomerId(@PathVariable(value = "id") int id) {
        try{
            return new ResponseEntity<>(service.getLastLoanByCustomerId(id), HttpStatus.OK);
        }
        catch (DataRetrievalFailureException exception){
            return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/book/{id}/last")
    public ResponseEntity<Loan> getLastLoanByBookId(@PathVariable(value = "id") int id) {
        try{
            return new ResponseEntity<>(service.getLastLoanByBookId(id), HttpStatus.OK);
        }
        catch (DataRetrievalFailureException exception){
            return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/loan/id/{id}")
    public ResponseEntity<Loan> updateLoanById(@PathVariable(value = "id") int id, Loan loan) {
        try{
            return new ResponseEntity<>(service.updateLoanById(id, loan), HttpStatus.OK);
        }
        catch (IllegalAccessException exception){
            return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (RecordDoesNotExistInDatabaseException exception){
            return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<Loan> saveLoan(@RequestBody Loan loan) {
        try{
            return new ResponseEntity<>(service.saveLoan(loan), HttpStatus.OK);
        }
        catch (ItemExistException exception){
            return new ResponseEntity(exception.getMessage(), HttpStatus.CONFLICT);
        }
        catch (DatabaseException exception){
            return new ResponseEntity(exception.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/loan/id/{id}")
    public ResponseEntity deleteLoanById(@PathVariable(value = "id") int id) {
        try{
            service.deleteLoanById(id);
            return new ResponseEntity("Loan has been deleted", HttpStatus.OK);
        }
        catch (DatabaseException exception){
            return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
