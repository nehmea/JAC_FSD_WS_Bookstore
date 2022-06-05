package com.jac.webservice.repository;

import com.jac.webservice.exceptions.DatabaseException;
import com.jac.webservice.exceptions.ItemExistException;
import com.jac.webservice.exceptions.RecordDoesNotExistInDatabaseException;
import com.jac.webservice.model.Customer;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    //    a helper function that checks if a customer exists by id
    public Boolean checkIfCustomerExistByID(int id) {
        String sql = "SELECT count(*) FROM customers WHERE id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, id);

        if (count == 0) {
            return false;
        }
        return true;
    }

    //    a helper function that checks for the number of customers by info in the database
    public int countCustomerByInfo(String firstName, String lastName, LocalDate dob) {
        String sql = "SELECT count(*) FROM customers WHERE first_name = ? AND last_name=? AND date_of_birth=?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, firstName, lastName, Date.valueOf(dob));

        return count;
    }

    //    a helper function that checks if a customer exists by Info in database
    public Boolean checkIfCustomerExistByInfo(String firstName, String lastName, LocalDate dob) {
        int count = countCustomerByInfo(firstName, lastName, dob);

        if (count == 0) {
            return false;
        }
        return true;
    }

    //  A function that returns all customers in the database
    public List<Customer> getAllCustomers() {
        String sql = "SELECT * FROM customers";
        return jdbcTemplate.query(sql, new CustomerRowMapper());
    }

    //  A function that returns a customer from the database by its id
    public Customer getCustomerById(int id) {
//        String sql = "SELECT count(*) FROM customers WHERE id = ?";
//        int count = jdbcTemplate.queryForObject(sql, Integer.class, id);

        if (!checkIfCustomerExistByID(id)) {
            throw new RecordDoesNotExistInDatabaseException("Customer does not exist in the database!");
        }

        String sql = "SELECT * FROM customers WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, customerRowMapper, id);
    }

    //  A function that returns a customer from the database by its name and DOB
    public Customer getCustomerByInfo(String firstName, String lastName, LocalDate dob) {

        int count = countCustomerByInfo(firstName, lastName, dob);

        if (count == 0) {
            throw new RecordDoesNotExistInDatabaseException("Customer does not exist in the database!");
        }

        if (count > 1) {
            throw new DatabaseException(count + "customers with the same Info exist in the database!");
        }

        String sql = "SELECT * FROM customers WHERE first_name = ? AND last_name=? AND date_of_birth=?";
        return jdbcTemplate.queryForObject(sql, customerRowMapper, firstName, lastName, Date.valueOf(dob));
    }

    //  A function that updates a customer in the database by its id
    public Customer updateCustomerById(int id, Customer customer) throws IllegalAccessException {

        if (!checkIfCustomerExistByID(id)) {
            throw new RecordDoesNotExistInDatabaseException("Customer does not exist in the database!");
        }

        Customer fetchedCustomer = getCustomerById(id);

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
                            "first_name=?," +
                            "middle_name=?," +
                            "last_name=?," +
                            "date_of_birth=?," +
                            "address=?," +
                            "city=?, " +
                            "state=?, " +
                            "zipcode=?, " +
                            "phone=?, " +
                            "registration_date=? " +
                            "WHERE id=?",
                    customer.getFirstName(), customer.getMiddleName(), customer.getLastName(),
                    (customer.getDob() == null) ? customer.getDob() : Date.valueOf(customer.getDob()),
                    customer.getAddress(), customer.getCity(),
                    customer.getState(), customer.getZipcode(), customer.getPhone(),
                    (customer.getRegistrationDate() == null) ? customer.getRegistrationDate() : Date.valueOf(customer.getRegistrationDate()),
                    id);

            return getCustomerById(id);

        } catch (Exception exc) {
            throw new DatabaseException(exc.getCause());
        }
    }


    //  A function that saves a new customer into the database
    public Customer saveCustomer(Customer customer) {

        Boolean customerExists = checkIfCustomerExistByInfo(customer.getFirstName(), customer.getLastName(), customer.getDob());

        if (customerExists) {
            throw new ItemExistException("Customer with the same info already exist in the database!");
        }

        try {
            jdbcTemplate.update("Insert into customers " +
                            "(first_name, middle_name, last_name, date_of_birth, address, city," +
                            "state, zipcode, phone, registration_date) " +
                            "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);",
                    customer.getFirstName(), customer.getMiddleName(), customer.getLastName(),
                    (customer.getDob() == null) ? customer.getDob() : Date.valueOf(customer.getDob()),
                    customer.getAddress(), customer.getCity(),
                    customer.getState(), customer.getZipcode(), customer.getPhone(),
                    (customer.getRegistrationDate() == null) ? customer.getRegistrationDate() : Date.valueOf(customer.getRegistrationDate())
            );
            int id = jdbcTemplate.queryForObject("select max(id) from customers", Integer.class);
            return getCustomerById(id);

        } catch (Exception exc) {
            throw new DatabaseException(ExceptionUtils.getRootCause(exc));
        }
    }


    //  A function that deletes a customer from the database by its id
    public void deleteCustomerById(int id) {

        if (!checkIfCustomerExistByID(id)) {
            throw new RecordDoesNotExistInDatabaseException("Record does not exist in the database!");
        }

        try {
            jdbcTemplate.update("delete from customers where id=?", id);
        } catch (Exception exc) {
            throw new DatabaseException(exc.getCause());
        }
    }

}


