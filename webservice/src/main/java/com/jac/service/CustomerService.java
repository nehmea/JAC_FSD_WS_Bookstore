package com.jac.service;

import com.jac.model.Customer;
import com.jac.model.Customer;
import com.jac.repository.CustomerRepository;
import com.jac.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository repository;

    //    get all customers
    public List<Customer> getAllCustomers() {
        return repository.getAllCustomers();
    }

    //    get customer by id
    public Customer getCustomerById(int id) {
        try {
            return repository.getCustomerById(id);
        } catch (DataRetrievalFailureException exc) {
            return null;
        }
    }

    //    get customer by info
    public Customer getCustomerByInfo(String firstName, String lastName, LocalDate dob) {
        try {
            return repository.getCustomerByInfo(firstName, lastName, dob);
        } catch (DataRetrievalFailureException exc) {
            return null;
        }
    }

    //    update customer by id
    public Customer updateCustomerById(int id, Customer customer) throws IllegalAccessException {
        return repository.updateCustomerById(id, customer);
    }

    //    save a new customer
    public Customer saveCustomer(Customer customer) {
        return repository.saveCustomer(customer);

    }

    //    delete a customer
    public void deleteCustomerById(int id) {
        repository.deleteCustomerById(id);
    }

}
