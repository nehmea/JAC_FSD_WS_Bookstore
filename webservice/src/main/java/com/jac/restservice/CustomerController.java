package com.jac.restservice;

import com.jac.exceptions.DatabaseException;
import com.jac.exceptions.ItemExistException;
import com.jac.exceptions.RecordDoesNotExistInDatabaseException;
import com.jac.model.Customer;
import com.jac.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    public CustomerService service;

    @GetMapping("")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        try {
            return new ResponseEntity<>(service.getAllCustomers(), HttpStatus.OK);
        }
        catch (DataRetrievalFailureException exception) {
            return new ResponseEntity(exception.getMessage(), HttpStatus.OK);
        }
    }

    @GetMapping("/customer/id/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") int id) {
        try{
            return new ResponseEntity<>(service.getCustomerById(id), HttpStatus.OK);
        }
        catch (DataRetrievalFailureException exception){
            return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/customer")
    public ResponseEntity<Customer> getCustomerByInfo(@RequestBody Customer customer) {
        String firstName = customer.getLastName();
        String lastName = customer.getLastName();
        LocalDate dob = customer.getDob();

        try{
            return new ResponseEntity<>(service.getCustomerByInfo(firstName, lastName, dob), HttpStatus.OK);
        }
        catch (DataRetrievalFailureException exception){
            return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/customer/id/{id}")
    public ResponseEntity<Customer> updateCustomerById(@PathVariable(value = "id") int id, Customer customer) {
        try{
            return new ResponseEntity<>(service.updateCustomerById(id, customer), HttpStatus.OK);
        }
        catch (IllegalAccessException exception){
            return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (RecordDoesNotExistInDatabaseException exception){
            return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer) {
        try{
            return new ResponseEntity<>(service.saveCustomer(customer), HttpStatus.OK);
        }
        catch (ItemExistException exception){
            return new ResponseEntity(exception.getMessage(), HttpStatus.CONFLICT);
        }
        catch (DatabaseException exception){
            return new ResponseEntity(exception.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/customer/id/{id}")
    public ResponseEntity deleteCustomerById(@PathVariable(value = "id") int id) {
        try{
            service.deleteCustomerById(id);
            return new ResponseEntity("Customer has been deleted", HttpStatus.OK);
        }
        catch (DatabaseException exception){
            return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
