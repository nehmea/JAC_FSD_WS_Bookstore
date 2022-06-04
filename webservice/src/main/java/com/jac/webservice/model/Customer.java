package com.jac.webservice.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Customer {
    private int id;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate dob;
    private String address;
    private String city;
    private String state;
    private String zipcode;
    private String phone;
    private LocalDate registrationDate;

    public Customer(String firstName, String lastName, LocalDate dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
    }

    public Customer(String firstName, String lastName, LocalDate dob, String address, String city, String state, String zipcode, String phone, LocalDate registrationDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.phone = phone;
        this.registrationDate = registrationDate;
    }
}
