package com.project.ebank.service;

import com.project.ebank.dtos.CustomerDTO;
import com.project.ebank.exceptions.CustomerNotFoundException;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> listCustomer();
    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CustomerDTO updateCustomer(CustomerDTO customerDTO);
    void deleteCustomer(Long costomerId);
    List<CustomerDTO> searchCustomer(String keyword);

}
