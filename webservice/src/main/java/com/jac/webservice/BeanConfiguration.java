package com.jac.webservice;

import com.jac.webservice.model.Customer;
import com.jac.webservice.repository.BookRowMapper;
import com.jac.webservice.repository.CustomerRowMapper;
import com.jac.webservice.repository.LoanRowMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public BookRowMapper BookRowMapper() {
        return new BookRowMapper();
    }

    @Bean
    public CustomerRowMapper BustomerRowMapper() {
        return new CustomerRowMapper();
    }

    @Bean
    public LoanRowMapper BoanRowMapper() {
        return new LoanRowMapper();
    }
}
