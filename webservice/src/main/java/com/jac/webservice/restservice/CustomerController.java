package com.jac.webservice.restservice;

import com.jac.webservice.exceptions.DatabaseException;
import com.jac.webservice.exceptions.GetRootException;
import com.jac.webservice.exceptions.ItemExistException;
import com.jac.webservice.exceptions.RecordDoesNotExistInDatabaseException;
import com.jac.webservice.model.Customer;
import com.jac.webservice.service.CustomerService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;



@RestController
@RequestMapping("/customers")
@CrossOrigin(maxAge = 60000)
public class CustomerController {

    @Autowired
    public CustomerService service;

    @GetMapping("")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        try {
            return new ResponseEntity<>(service.getAllCustomers(), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(String.format("Can't retrieve customers from the database %n" +
                    "Root Error Cause: %s%n" +
                    "Root Error Message: %s",
                    NestedExceptionUtils.getMostSpecificCause(e),
                    ExceptionUtils.getRootCauseMessage(e)),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/customer/id/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") int id) {
        try{
            return new ResponseEntity<>(service.getCustomerById(id), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(String.format("Can't retrieve customer (%d) from the database %n" +
                    "Root Error Cause: %s%n" +
                    "Root Error Message: %s",
                    id,
                    NestedExceptionUtils.getMostSpecificCause(e),
                    ExceptionUtils.getRootCauseMessage(e)),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/customer")
    public ResponseEntity<Customer> getCustomerByInfo(@RequestBody Customer customer) {
        String firstName = customer.getFirstName();
        String lastName = customer.getLastName();
        LocalDate dob = customer.getDob();

        try{
            return new ResponseEntity<>(service.getCustomerByInfo(firstName, lastName, dob), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(String.format("Can't retrieve customer (%s) from the database%n" +
                            "Root Error Cause: %s%n" +
                            "Root Error Message: %s",
                    customer,
                    NestedExceptionUtils.getMostSpecificCause(e),
                    ExceptionUtils.getRootCauseMessage(e)),
                    HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/customer/id/{id}")
    public ResponseEntity<Customer> updateCustomerById(@PathVariable(value = "id") int id, @RequestBody Customer customer) {
        try{
            return new ResponseEntity<>(service.updateCustomerById(id, customer), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(String.format("Can't modify customer (%s) from the database %n" +
                            "Root Error Cause: %s%n" +
                            "Root Error Message: %s",
                    customer,
                    NestedExceptionUtils.getMostSpecificCause(e),
                    ExceptionUtils.getRootCauseMessage(e)),
                    HttpStatus.NOT_FOUND);
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
        catch (Exception e){
            return new ResponseEntity(String.format("Can't save customer (%s) into the database%n %n" +
                            "Root Error Cause: %s%n" +
                            "Root Error Message: %s",
                    customer,
                    NestedExceptionUtils.getMostSpecificCause(e),
                    ExceptionUtils.getRootCauseMessage(e)),
                    HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/customer/id/{id}")
    public ResponseEntity deleteCustomerById(@PathVariable(value = "id") int id) {
        try{
            service.deleteCustomerById(id);
            return new ResponseEntity("Customer has been deleted", HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(String.format("Can't delete customer (%d) from the database%n" +
                            "Root Error Cause: %s%n" +
                            "Root Error Message: %s",
                    id,
                    NestedExceptionUtils.getMostSpecificCause(e),
                    ExceptionUtils.getRootCauseMessage(e)),
                    HttpStatus.NOT_FOUND);
        }
    }

}
