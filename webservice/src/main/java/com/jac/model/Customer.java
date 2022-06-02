package com.jac.model;

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
}
