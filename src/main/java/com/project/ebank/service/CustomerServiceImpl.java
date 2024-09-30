package com.project.ebank.service;

import com.project.ebank.dtos.CustomerDTO;
import com.project.ebank.entities.Customer;
import com.project.ebank.exceptions.CustomerNotFoundException;
import com.project.ebank.mappers.CurrentBankAccountMapper;
import com.project.ebank.mappers.CustomerMapper;
import com.project.ebank.mappers.SavingBankAccountMapper;
import com.project.ebank.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
@Transactional
@Service
@AllArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    CustomerRepository customerRepository;
    CustomerMapper customerMapper;

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("saving new customer ...");
        CustomerDTO savedCustomer = customerMapper.fromCustomer(customerRepository.save(customerMapper.fromCustomerDTO(customerDTO)));
        log.info("customer saved ");
        return savedCustomer;
    }
    @Override
    public List<CustomerDTO> searchCustomer(String keyword) {
        return customerRepository.searchCustomers(keyword).stream().map(cust -> customerMapper.fromCustomer(cust)).collect(Collectors.toList());
    }
    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {

        CustomerDTO savedCustomer = customerMapper.fromCustomer(customerRepository.save(customerMapper.fromCustomerDTO(customerDTO)));

        return savedCustomer;
    }
    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElseThrow(()->
                new CustomerNotFoundException("customer not found")
        );
        return customerMapper.fromCustomer(customer) ;
    }
    @Override
    public List<CustomerDTO> listCustomer() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> customersDTO = customers.stream().map(customer -> customerMapper.fromCustomer(customer)).collect(Collectors.toList());
        return customersDTO;
    }

    @Override
    public void deleteCustomer(Long costomerId){
        customerRepository.deleteById(costomerId);
    }
}
