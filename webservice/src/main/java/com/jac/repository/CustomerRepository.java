package com.jac.repository;

import com.jac.exceptions.DatabaseException;
import com.jac.exceptions.RecordDoesNotExistInDatabaseException;
import com.jac.model.Book;
import com.jac.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public class CustomerRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    CustomerRowMapper customerRowMapper;

    //  A function that returns all customers in the database
    public List<Customer> getAllCustomers() {

        try {
            String sql = "SELECT * FROM customers";
            return jdbcTemplate.query(sql, new CustomerRowMapper());

        } catch (Exception exception) {
            throw new DataRetrievalFailureException("An Exception occurred in CustomerRepository.getAllCustomers()");
        }
    }

    //  A function that returns a customer from the database by its id
    public Customer getCustomerById(int id) {
        try {
            String sql = "SELECT * FROM customers WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, customerRowMapper, id);
        } catch (Exception exc) {
            throw new DataRetrievalFailureException("An Exception occurred in CustomerRepository.getBookById" + id);
        }
    }

    //  A function that returns a customer from the database by its name and DOB
    public Customer getCustomerByInfo(String firstName, String lastName, LocalDate dob) {
        try {
            String sql = "SELECT * FROM customers WHERE first_name = ? AND last_name=? AND date_of_birth=?";
            return jdbcTemplate.queryForObject(sql, customerRowMapper, firstName, lastName, Date.valueOf(dob));
        } catch (Exception exc) {
            throw new DataRetrievalFailureException("An Exception occurred in CustomerRepository.getCustomerByInfo");
        }
    }

    //  A function that updates a customer in the database by its id
    public Customer updateCustomerById(int id, Customer customer) throws IllegalAccessException {
        Customer fetchedCustomer = getCustomerById(id);

        if(fetchedCustomer == null) {
            throw new RecordDoesNotExistInDatabaseException("Failed to retrieve a Loan with id " + id);
        }

        // returns the array of Field objects
        for (Field f : customer.getClass().getFields()) {
            f.setAccessible(true);
            if (f.get(customer) == null) {
                f.set(customer, f.get(fetchedCustomer));
            }
        }

        try {
            jdbcTemplate.update(
                    "UPDATE customers set " +
                            "firs_name=?," +
                            "middle_name=?" +
                            "last_name=?"+
                            "date_of_birth=?," +
                            "address=? " +
                            "city=? " +
                            "state=? " +
                            "zipcode=? " +
                            "phone=? " +
                            "registration_date=? " +
                            "WHERE id=?",
                    customer.getFirstName(), customer.getMiddleName(),customer.getLastName(),
                    Date.valueOf(customer.getDob()), customer.getAddress(),customer.getCity(),
                    customer.getState(),customer.getZipcode(),customer.getPhone(),
                    Date.valueOf(customer.getRegistrationDate()),
                    id);
            return getCustomerById(id);
        } catch (Exception exc) {
            throw new DatabaseException("An exception occurred in CustomerRepository.updateCustomerById " + id);
        }
    }

    //  A function that saves a new customer into the database
    public Customer saveCustomer(Customer customer) {
        try {
            jdbcTemplate.update("Insert into customers " +
                            "firs_name, middle_name, last_name, date_of_birth, address, city," +
                            "state, zipcode, phone, registration_date " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);",
                    customer.getFirstName(), customer.getMiddleName(), customer.getLastName(),
                    Date.valueOf(customer.getDob()), customer.getAddress(), customer.getCity(),
                    customer.getState(), customer.getZipcode(), customer.getPhone(),
                    Date.valueOf(customer.getRegistrationDate())
            );
            int id = jdbcTemplate.queryForObject("select max(id) from customers", Integer.class);
            return getCustomerById(id);

        }
        catch (Exception exc) {
            throw new NullPointerException("An exception occurred in CustomerRepository.saveCustomer");
        }
    }

    //  A function that deletes a customer from the database by its id
    public void deleteCustomerById(int id) {
        try {
            jdbcTemplate.update("delete from customers where id=?", id);
        }
        catch (Exception exc) {
            throw new DatabaseException("an exception occurred in CustomerRepository.deleteCustomerById " + id);
        }
    }

}
