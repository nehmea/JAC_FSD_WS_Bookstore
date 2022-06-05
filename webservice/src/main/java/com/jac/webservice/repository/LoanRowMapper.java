package com.jac.webservice.repository;

import com.jac.webservice.model.Loan;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoanRowMapper implements RowMapper<Loan> {

    @Override
    public Loan mapRow(ResultSet rs, int rowNum) throws SQLException {

        Loan loan = new Loan();
        loan.setId(rs.getInt("id"));
        loan.setCustomerId(rs.getInt("customer_id"));
        loan.setBookId(rs.getInt("book_id"));

        if ((rs.getDate("date_out") == null)) {
            loan.setDateOut(null);
        } else {
            loan.setDateOut(rs.getDate("date_out").toLocalDate());
        }

        if ((rs.getDate("date_in") == null)) {
            loan.setDateIn(null);
        } else {
            loan.setDateIn(rs.getDate("date_in").toLocalDate());
        }

        return loan;
    }

    }
