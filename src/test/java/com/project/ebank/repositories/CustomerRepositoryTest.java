package com.project.ebank.repositories;

import com.project.ebank.entities.Customer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class CustomerRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;
    @BeforeEach
    void  beforeStart(){
        Customer customer=Customer.builder()
                .name("user")
                .email("user@gmail.com")
                .cin("RR4321")
                .password("azerty")
                .build();
        customerRepository.save(customer);
    }
    @Test
    void findCustomersByEmail() {
        Customer expected = customerRepository.findCustomersByEmail("user@gmail.com");
        Assertions.assertNotNull(expected);
    }
    @Test
    void findCustomersByCin() {
        List<Customer> expected = customerRepository.findCustomersByCin("RR4321");
        Assertions.assertNotNull(expected);
    }
    @AfterEach
    void AfterTesting(){
        customerRepository.deleteAll();
    }
}