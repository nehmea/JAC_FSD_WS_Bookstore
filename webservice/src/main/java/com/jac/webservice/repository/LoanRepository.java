package com.jac.webservice.repository;

import com.jac.webservice.exceptions.DatabaseException;
import com.jac.webservice.exceptions.ItemExistException;
import com.jac.webservice.exceptions.RecordDoesNotExistInDatabaseException;
import com.jac.webservice.model.Book;
import com.jac.webservice.model.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LoanRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    LoanRowMapper loanRowMapper;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CustomerRepository customerRepository;

    //  A function that returns all loans in the database
    public List<Loan> getAllLoans() {
        String sql = "SELECT * FROM loans";
        return jdbcTemplate.query(sql, loanRowMapper);
    }

    //  A function that returns a loan from the database by its id
    public Loan getLoanById(int id) {

        if (!checkIfLoanExistByID(id)) {
            throw new RecordDoesNotExistInDatabaseException("A loan with id=" + id + " does not exist in the database!");
        }
        String sql = "SELECT * FROM loans WHERE id=?";
        return jdbcTemplate.queryForObject(sql, loanRowMapper, id);

    }

    //  A function that returns list of loans from the database by customerId
    public List<Loan> getLoanListByCustomerId(int customerId) {

        if (!checkIfLoanExistByCustomerID(customerId)) {
            throw new RecordDoesNotExistInDatabaseException("No loans exist for customer id=" + customerId);
        }
        String sql = "SELECT * FROM loans WHERE customer_id=?";
        return jdbcTemplate.query(sql, loanRowMapper, customerId);

    }

    //  A function that returns list of loans from the database by bookId
    public List<Loan> getLoanListByBookId(int bookId) {
        if (!checkIfLoanExistByBookID(bookId)) {
            throw new RecordDoesNotExistInDatabaseException("No loans exist for book id=" + bookId);
        }
        String sql = "SELECT * FROM loans WHERE book_id=?";
        return jdbcTemplate.query(sql, loanRowMapper, bookId);

    }

    //  A function that returns list of loans from the database by book isbn
    public List<Loan> getLoanListByBookISBN(String isbn) {
        if (!bookRepository.checkIfBookExistByISBN(isbn)) {
            throw new RecordDoesNotExistInDatabaseException("No Book exists with isbn=" + isbn);
        }

        int book_id = bookRepository.getBookByISBN(isbn).getId();

        String sql = "SELECT * FROM loans WHERE book_id=?";
        return jdbcTemplate.query(sql, loanRowMapper, book_id);

    }

    //  A function that returns the last loan by a customerId
    public Loan getLastLoanByCustomerId(int customerId) {

        if (!checkIfLoanExistByCustomerID(customerId)) {
            throw new RecordDoesNotExistInDatabaseException("No loans exist for customer id=" + customerId);
        }

        List<Loan> loans = getLoanListByCustomerId(customerId);
        LocalDate last_date = loans.get(0).getDateOut();
        for (Loan loan : loans) {
            if (loan.getDateOut().isAfter(last_date)) {
                last_date = loan.getDateOut();
            }
        }

        return jdbcTemplate.queryForObject(
                "select * from loans WHERE customer_id=? AND date_out=?",
                loanRowMapper,
                customerId,
                Date.valueOf(last_date)
        );

    }

    //  A function that returns the last loan by a bookId
    public Loan getLastLoanByBookId(int bookId) {

        if (!checkIfLoanExistByBookID(bookId)) {
            throw new RecordDoesNotExistInDatabaseException("No loans exist for book id=" + bookId);
        }

        List<Loan> loans = getLoanListByBookId(bookId);
        LocalDate last_date = loans.get(0).getDateOut();
        for (Loan loan : loans) {
            if (loan.getDateOut().isAfter(last_date)) {
                last_date = loan.getDateOut();
            }
        }

        return jdbcTemplate.queryForObject(
                "select * from loans WHERE book_id=? AND date_out=?",
                loanRowMapper,
                bookId,
                Date.valueOf(last_date)
        );
    }

    //  A function that updates a loan in the database by its id
    public Loan updateLoanById(int id, Loan loan) throws IllegalAccessException {

        if (!checkIfLoanExistByID(id)) {
            throw new RecordDoesNotExistInDatabaseException("Failed to retrieve a Loan with id=" + id);
        }

        Loan fetchedLoan = getLoanById(id);

        // returns the array of Field objects and replace empty ones from fetched record
        for (Field f : loan.getClass().getFields()) {
            f.setAccessible(true);
            if (f.get(loan) == null) {
                f.set(loan, f.get(fetchedLoan));
            }
        }

        jdbcTemplate.update(
                "UPDATE loans set " +
                        "customer_id=?," +
                        "book_id=?," +
                        "date_out=?," +
                        "date_in=? " +
                        "WHERE id=?",
                loan.getCustomerId(),
                loan.getBookId(),
                (loan.getDateOut() == null) ? loan.getDateOut() : Date.valueOf(loan.getDateOut()),
                (loan.getDateIn() == null) ? loan.getDateIn() : Date.valueOf(loan.getDateIn()),
                id);

        return getLoanById(id);
    }

    //  A function that updates the dateIn of a loan by its id
    public Loan updateLoanDateIn(int id, LocalDate dateIn) {

        if (!checkIfLoanExistByID(id)) {
            throw new RecordDoesNotExistInDatabaseException("Failed to retrieve a Loan with id=" + id);
        }

        jdbcTemplate.update(
                "UPDATE loans set date_in=? WHERE id=?",
                (dateIn == null) ? dateIn : Date.valueOf(dateIn),
                id);

        return getLoanById(id);

    }

    //  A function that saves a new loan into the database
    public Loan saveLoan(Loan loan) {

        int customerID = loan.getCustomerId();
        int bookID = loan.getBookId();
        LocalDate dateOut = loan.getDateOut();

        if (!customerRepository.checkIfCustomerExistByID(customerID)) {
            throw new ItemExistException("No customer Exist with id=" + customerID);
        }

        if (!bookRepository.checkIfBookExistByID(bookID)) {
            throw new ItemExistException("No book Exist with id=" + bookID);
        }

        if (checkIfLoanExistByBookInfo(customerID, bookID, dateOut)) {
            throw new ItemExistException("A record already exist with the same customer ID, book ID, and date out!");
        }

        jdbcTemplate.update("Insert into loans " +
                        "(customer_id, book_id, date_out) " +
                        "values (?, ?, ?);",
                customerID, bookID, Date.valueOf(dateOut)
        );
        int id = jdbcTemplate.queryForObject("select max(id) from loans", Integer.class);
        return getLoanById(id);

    }

    //  A function that deletes a loan from the database by its id
    public void deleteLoanById(int id) {

        if (!checkIfLoanExistByID(id)) {
            throw new RecordDoesNotExistInDatabaseException("No loan exists with id=" + id);
        }
        jdbcTemplate.update("delete from loans where id=?", id);

    }

    //A function that checks if a loan exists by id
    public Boolean checkIfLoanExistByID(int id) {
        String sql = "SELECT count(*) FROM loans WHERE id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, id);

        return count != 0;
    }

    //A function that checks if a loan exists by customer id
    public Boolean checkIfLoanExistByCustomerID(int id) {
        String sql = "SELECT count(*) FROM loans WHERE customer_id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, id);

        return count != 0;
    }

    //A function that checks if a loan exists by book id
    public Boolean checkIfLoanExistByBookID(int id) {
        String sql = "SELECT count(*) FROM loans WHERE book_id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, id);

        return count != 0;
    }

    //A function that checks if a loan exists by info
    public Boolean checkIfLoanExistByBookInfo(int customerID, int bookId, LocalDate dateOut) {
        String sql = "SELECT count(*) FROM loans WHERE customer_id=? AND book_id=? AND date_out=?";
        int count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                customerID, bookId, Date.valueOf(dateOut)
        );

        return count != 0;
    }
}
