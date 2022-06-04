package com.jac.webservice.repository;

import com.jac.webservice.model.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRowMapper implements RowMapper<Customer> {

    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {

        Customer customer = new Customer();
        customer.setId(rs.getInt("id"));
        customer.setFirstName(rs.getString("first_name"));
        customer.setMiddleName(rs.getString("middle_name"));
        customer.setLastName(rs.getString("last_name"));
        customer.setDob(rs.getDate("date_of_birth").toLocalDate());
        customer.setAddress(rs.getString("address"));
        customer.setCity(rs.getString("city"));
        customer.setZipcode(rs.getString("zipcode"));
        customer.setPhone(rs.getString("phone"));
        customer.setRegistrationDate(rs.getDate("registration_date").toLocalDate());

        return customer;
    }
}
