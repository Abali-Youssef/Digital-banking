package com.project.ebank.service;

import com.project.ebank.dtos.request.CustomerRequestDTO;
import com.project.ebank.dtos.response.CustomerResponseDTO;
import com.project.ebank.dtos.response.lists.CustomerResponseDTOList;
import com.project.ebank.entities.Customer;
import com.project.ebank.exceptions.CustomerNotFoundException;

import java.util.List;

public interface CustomerService {
    List<CustomerResponseDTO> getCustomer(String cin) throws CustomerNotFoundException;


    CustomerResponseDTO saveCustomer(CustomerRequestDTO customerDTO);
    CustomerResponseDTO updateCustomer(CustomerRequestDTO customerDTO,Long id);

    CustomerResponseDTOList listCustomer(String keyword, int page, int size);
    Customer loadCustomerByEmail (String email);
    void deleteCustomer(List<Long> customerIds);

}
