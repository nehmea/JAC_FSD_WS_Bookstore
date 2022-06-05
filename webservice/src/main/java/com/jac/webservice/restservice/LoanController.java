package com.jac.webservice.restservice;

import com.jac.webservice.exceptions.DatabaseException;
import com.jac.webservice.exceptions.ItemExistException;
import com.jac.webservice.exceptions.RecordDoesNotExistInDatabaseException;
import com.jac.webservice.model.Loan;
import com.jac.webservice.service.LoanService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
@CrossOrigin(maxAge = 60000)
public class LoanController {

    @Autowired
    public LoanService service;

    @GetMapping("")
    public ResponseEntity<List<Loan>> getAllLoans() {
        try {
            return new ResponseEntity<>(service.getAllLoans(), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(String.format("Unable to fetch loans from the database %n" +
                            "Root Error Cause: %s%n" +
                            "Root Error Message: %s",
                    NestedExceptionUtils.getMostSpecificCause(e),
                    ExceptionUtils.getRootCauseMessage(e)),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/loan/id/{id}")
    public ResponseEntity<Loan> getLoanById(@PathVariable(value = "id") int id) {
        try{
            return new ResponseEntity<>(service.getLoanById(id), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(String.format("Unable to fetch loan (id = %d) from the database %n" +
                            "Root Error Cause: %s%n" +
                            "Root Error Message: %s",
                    id,
                    NestedExceptionUtils.getMostSpecificCause(e),
                    ExceptionUtils.getRootCauseMessage(e)),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/customer/id/{id}")
    public ResponseEntity<List<Loan>> getLoansByCustomerId(@PathVariable(value = "id") int id) {
        try{
            return new ResponseEntity<>(service.getLoansByCustomerId(id), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(String.format("Unable to fetch loans of customer (id = %d) from the database %n" +
                            "Root Error Cause: %s%n" +
                            "Root Error Message: %s",
                    id,
                    NestedExceptionUtils.getMostSpecificCause(e),
                    ExceptionUtils.getRootCauseMessage(e)),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/book/id/{id}")
    public ResponseEntity<List<Loan>> getLoansByBookId(@PathVariable(value = "id") int id) {
        try{
            return new ResponseEntity<>(service.getLoansByBookId(id), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(String.format("Unable to fetch loans of book (id = %d) from the database %n" +
                            "Root Error Cause: %s%n" +
                            "Root Error Message: %s",
                    id,
                    NestedExceptionUtils.getMostSpecificCause(e),
                    ExceptionUtils.getRootCauseMessage(e)),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/book/isbn/{isbn}")
    public ResponseEntity<List<Loan>> getLoansByBookISBN(@PathVariable(value = "isbn") String isbn) {
        try{
            return new ResponseEntity<>(service.getLoansByBookISBN(isbn), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(String.format("Unable to fetch loans of book (isbn = %s) from the database %n" +
                            "Root Error Cause: %s%n" +
                            "Root Error Message: %s",
                    isbn,
                    NestedExceptionUtils.getMostSpecificCause(e),
                    ExceptionUtils.getRootCauseMessage(e)),
                    HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/customer/{id}/last")
    public ResponseEntity<Loan> getLastLoanByCustomerId(@PathVariable(value = "id") int id) {
        try{
            return new ResponseEntity<>(service.getLastLoanByCustomerId(id), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(String.format("Unable to fetch last loan of customer (id = %d) from the database %n" +
                            "Root Error Cause: %s%n" +
                            "Root Error Message: %s",
                    id,
                    NestedExceptionUtils.getMostSpecificCause(e),
                    ExceptionUtils.getRootCauseMessage(e)),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/book/{id}/last")
    public ResponseEntity<Loan> getLastLoanByBookId(@PathVariable(value = "id") int id) {
        try{
            return new ResponseEntity<>(service.getLastLoanByBookId(id), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(String.format("Unable to fetch last loan of book (id = %d) from the database %n" +
                            "Root Error Cause: %s%n" +
                            "Root Error Message: %s",
                    id,
                    NestedExceptionUtils.getMostSpecificCause(e),
                    ExceptionUtils.getRootCauseMessage(e)),
                    HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/loan/id/{id}")
    public ResponseEntity<Loan> updateLoanById(@PathVariable(value = "id") int id, @RequestBody Loan loan) {
        try{
            return new ResponseEntity<>(service.updateLoanById(id, loan), HttpStatus.OK);
        }
        catch (RecordDoesNotExistInDatabaseException exception){
            return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity(String.format("Unable to update loan (id = %d) in the database %n" +
                            "Root Error Cause: %s%n" +
                            "Root Error Message: %s",
                    id,
                    NestedExceptionUtils.getMostSpecificCause(e),
                    ExceptionUtils.getRootCauseMessage(e)),
                    HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/loan/id/{id}/date-in")
    public ResponseEntity<Loan> updateLoanDateIn(@PathVariable(value = "id") int id, @RequestBody Loan loan) {
        try{
            return new ResponseEntity<>(service.updateLoanDateIn(id, loan.getDateIn()), HttpStatus.OK);
        }
        catch (RecordDoesNotExistInDatabaseException exception){
            return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity(String.format("Unable to update loan (id = %d) in the database %n" +
                            "Root Error Cause: %s%n" +
                            "Root Error Message: %s",
                    id,
                    NestedExceptionUtils.getMostSpecificCause(e),
                    ExceptionUtils.getRootCauseMessage(e)),
                    HttpStatus.NOT_FOUND);
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
        catch (Exception e){
            return new ResponseEntity(String.format("Unable to save new loan (%s) into the database %n" +
                            "Root Error Cause: %s%n" +
                            "Root Error Message: %s",
                    loan,
                    NestedExceptionUtils.getMostSpecificCause(e),
                    ExceptionUtils.getRootCauseMessage(e)),
                    HttpStatus.NOT_FOUND);
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
        catch (Exception e){
            return new ResponseEntity(String.format("Unable to delete loan (id = %d) from the database %n" +
                            "Root Error Cause: %s%n" +
                            "Root Error Message: %s",
                    id,
                    NestedExceptionUtils.getMostSpecificCause(e),
                    ExceptionUtils.getRootCauseMessage(e)),
                    HttpStatus.NOT_FOUND);
        }
    }
}
