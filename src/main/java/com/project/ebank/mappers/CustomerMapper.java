package com.project.ebank.mappers;


import com.project.ebank.dtos.request.CustomerRequestDTO;
import com.project.ebank.dtos.response.CustomerResponseDTO;
import com.project.ebank.entities.BankAccount;
import com.project.ebank.entities.CurrentAccount;
import com.project.ebank.entities.Customer;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer fromCustomerDTO(CustomerRequestDTO customerRequestDTO);
    CustomerResponseDTO fromCustomer(Customer customer);
}
