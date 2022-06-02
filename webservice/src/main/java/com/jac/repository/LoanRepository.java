package com.jac.repository;

import com.jac.exceptions.DatabaseException;
import com.jac.exceptions.RecordDoesNotExistInDatabaseException;
import com.jac.model.Book;
import com.jac.model.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public class LoanRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    LoanRowMapper loanRowMapper;

    @Autowired
    BookRepository bookRepository;

    //  A function that returns all loans in the database
    public List<Loan> getAllLoans() {

        try {
            String sql = "SELECT * FROM loans";
            return jdbcTemplate.query(sql, loanRowMapper);

        } catch (Exception exception) {
            throw new DataRetrievalFailureException("An Exception occurred in LoanRepository.getAllLoans()");
        }
    }

    //  A function that returns a loan from the database by its id
    public Loan getLoanById(int id) {
        try {
            String sql = "SELECT * FROM loans WHERE id=?";
            return jdbcTemplate.queryForObject(sql, loanRowMapper,id);
        } catch (Exception exc) {
            throw new DataRetrievalFailureException("An Exception occurred in LoanRepository.getLoanById" + id);
        }
    }

    //  A function that returns list of loans from the database by customerId
    public List<Loan> getLoanListByCustomerId(int customerId) {
        try {
            String sql = "SELECT * FROM loans WHERE customer_id=?";
            return jdbcTemplate.query(sql, loanRowMapper, customerId);
        } catch (Exception exc) {
            throw new DataRetrievalFailureException("An Exception occurred in LoanRepository.getLoanListByCustomerId");
        }
    }

    //  A function that returns list of loans from the database by bookId
    public List<Loan> getLoanListByBookId(int bookId) {
        try {
            String sql = "SELECT * FROM loans WHERE customer_id=?";
            return jdbcTemplate.query(sql, loanRowMapper, bookId);
        } catch (Exception exc) {
            throw new DataRetrievalFailureException("An Exception occurred in LoanRepository.getLoanListByBookId");
        }
    }

    //  A function that returns list of loans from the database by book isbn
    public List<Loan> getLoanListByBookISBN(String isbn) {
        Book fetchedBook = bookRepository.getBookByISBN(isbn);
        if(fetchedBook == null) {
            throw new RecordDoesNotExistInDatabaseException("Failed to retrieve a Loan with isbn " + isbn);
        }
        try {
            String sql = "SELECT * FROM loans WHERE customer_id=?";
            return jdbcTemplate.query(sql, loanRowMapper, fetchedBook.getId());
        } catch (Exception exc) {
            throw new DataRetrievalFailureException("An Exception occurred in LoanRepository.getLoanListByBookId");
        }
    }

    //  A function that returns the last loan by a customerId
    public Loan getLastLoanByCustomerId(int customerId) {
        try {
            int loan_id = jdbcTemplate.queryForObject("select max(id) from loans WHERE customer_id=?", Integer.class, customerId);
            String sql = "SELECT * FROM loans WHERE id=?";
            return jdbcTemplate.queryForObject(sql, loanRowMapper, loan_id);
        } catch (Exception exc) {
            throw new DataRetrievalFailureException("An Exception occurred in LoanRepository.getLastLoanByCustomerId");
        }
    }

    //  A function that returns the last loan by a bookId
    public Loan getLastLoanByBookId(int bookId) {
        try {
            int loan_id = jdbcTemplate.queryForObject("select max(id) from loans WHERE book_id=?", Integer.class, bookId);
            String sql = "SELECT * FROM loans WHERE id=?";
            return jdbcTemplate.queryForObject(sql, loanRowMapper, loan_id);
        } catch (Exception exc) {
            throw new DataRetrievalFailureException("An Exception occurred in LoanRepository.getLastLoanByBookId");
        }
    }

    //  A function that updates a loan in the database by its id
    public Loan updateLoanById(int id, Loan loan) throws IllegalAccessException {
        Loan fetchedLoan = getLoanById(id);

        if(fetchedLoan == null) {
            throw new RecordDoesNotExistInDatabaseException("Failed to retrieve a Loan with id " + id);
        }

        // returns the array of Field objects and replace empty ones from fetched record
        for (Field f : loan.getClass().getFields()) {
            f.setAccessible(true);
            if (f.get(loan) == null) {
                f.set(loan, f.get(fetchedLoan));
            }
        }

        try {
            jdbcTemplate.update(
                    "UPDATE loans set " +
                            "customer_id=?," +
                            "book_id=?" +
                            "date_out=?" +
                            "date_in=?," +
                            "WHERE id=?",
                    loan.getCustomerId(),
                    loan.getBookId(),
                    Date.valueOf(loan.getDateOut()),
                    Date.valueOf(loan.getDateIn()),
                    id);

            return getLoanById(id);
        } catch (Exception exc) {
            throw new DatabaseException("An exception occurred in updateLoan " + id);
        }
    }

    //  A function that updates the dateIn of a loan by its id
    public Loan updateLoanDateIn(int id, LocalDate dateIn) {
        Loan fetchedLoan = getLoanById(id);

        if(fetchedLoan == null) {
            throw new RecordDoesNotExistInDatabaseException("Failed to retrieve a Loan with id " + id);
        }

        try {
            jdbcTemplate.update(
                    "UPDATE loans set date_in=? WHERE id=?",
                    Date.valueOf(dateIn), id);

            return getLoanById(id);
        } catch (Exception exc) {
            throw new DatabaseException("An exception occurred in updateLoan " + id);
        }
    }

    //  A function that saves a new loan into the database
    public Loan saveLoan(Loan loan) {
        try {
            jdbcTemplate.update("Insert into loans " +
                            "customer_id, book_id, date_out, " +
                            "values (?, ?, ?, ?);",
                    loan.getCustomerId(), loan.getBookId(), Date.valueOf(loan.getDateOut())
            );
            int id = jdbcTemplate.queryForObject("select max(id) from loans", Integer.class);
            return getLoanById(id);

        } catch (Exception exc) {
            throw new NullPointerException("An exception occurred in saveLoan");
        }
    }

    //  A function that deletes a loan from the database by its id
    public void deleteLoanById(int id) {
        try {
            jdbcTemplate.update("delete from loans where id=?", id);
        } catch (Exception exc) {
            throw new DatabaseException("an exception occurred in deleteLoanById " + id);
        }
    }
}
