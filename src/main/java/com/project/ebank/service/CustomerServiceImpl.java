package com.project.ebank.service;

import com.project.ebank.dtos.request.CustomerRequestDTO;
import com.project.ebank.dtos.response.CustomerResponseDTO;
import com.project.ebank.dtos.response.lists.CustomerResponseDTOList;
import com.project.ebank.entities.Customer;
import com.project.ebank.exceptions.CardNotFoundException;
import com.project.ebank.exceptions.CustomerAlreadyExistsException;
import com.project.ebank.exceptions.CustomerNotFoundException;
import com.project.ebank.exceptions.NonDeletabeleRessourceException;
import com.project.ebank.mappers.CustomerMapper;
import com.project.ebank.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Transactional
@Service
@AllArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    CustomerRepository customerRepository;
    CustomerMapper customerMapper;
    PasswordEncoder passwordEncoder;
SecurityService securityService;
    @Override
    public CustomerResponseDTO saveCustomer(CustomerRequestDTO customerDTO) {
        if(customerRepository.findCustomersByEmail(customerDTO.getEmail() )!= null){
            throw new CustomerAlreadyExistsException("Customer already exists, try to change your email");
        }
        log.info("saving new customer ...");
        customerDTO.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
        Customer customer = customerMapper.fromCustomerDTO(customerDTO);

        CustomerResponseDTO savedCustomer = customerMapper.fromCustomer(customerRepository.save(customer));
        securityService.addRoleToUser(savedCustomer.getEmail(),"user");
        log.info("customer saved ");
        return savedCustomer;
    }
//    @Override
//    public List<CustomerResponseDTO> searchCustomer(String keyword,int page,int size) {
//        return customerRepository.searchCustomers(keyword,PageRequest.of(page,size)).stream().map(cust -> customerMapper.fromCustomer(cust)).collect(Collectors.toList());
//    }
    @Override
    public CustomerResponseDTO updateCustomer(CustomerRequestDTO customerDTO,Long id) {
        Customer customer=customerRepository.findById(id).orElseThrow(()->
                new CustomerNotFoundException("Customer not found")
        );
        if(customerRepository.findCustomersByEmail(customerDTO.getEmail() )!= null && (!customer.getEmail().equals(customerDTO.getEmail()))){
            throw new CustomerAlreadyExistsException("Customer already exists, try to change your email");
        }

        customer.setPassword(customerDTO.getPassword());
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setCin(customerDTO.getCin());
        CustomerResponseDTO savedCustomer = customerMapper.fromCustomer(customerRepository.save(customer));

        return savedCustomer;
    }
    @Override
    public List<CustomerResponseDTO> getCustomer(String cin) throws CustomerNotFoundException {
        List<CustomerResponseDTO> customers=customerRepository.findCustomersByCin(cin).stream().map(c->customerMapper.fromCustomer(c)).collect(Collectors.toList());
        return customers ;
    }
    @Override
    public CustomerResponseDTOList listCustomer(String keyword, int page, int size) {
        Page<Customer> customers = customerRepository.findCustomersByKeyword(keyword,PageRequest.of(page,size));

        if(keyword==""){
             customers = customerRepository.findAll(PageRequest.of(page,size));

        }
        List<CustomerResponseDTO> customersDTO = customers.getContent().stream().map(customer -> customerMapper.fromCustomer(customer)).collect(Collectors.toList());
        return new CustomerResponseDTOList(customersDTO,page+1,customers.getTotalPages(), customers.getTotalElements(),size);
    }

    @Override
    public Customer loadCustomerByEmail(String email) {
        Customer customersByEmail = customerRepository.findCustomersByEmail(email);
        if(customersByEmail == null) throw new CustomerNotFoundException("Customer with email "+email+" not found");
        return customersByEmail;
    }

    @Override
    public void deleteCustomer(List<Long> customerIds){
        customerIds.forEach(cust ->{
            Customer customer = customerRepository.findById(cust).orElseThrow(() -> new CustomerNotFoundException("Customer with Id" + cust + " not found"));
            if(!customer.getBankAccounts().isEmpty()){
                throw new NonDeletabeleRessourceException("Customer with id="+cust+" is related, try to delete all related accounts");

            }
        });
        customerRepository.deleteAllById(customerIds);
    }
}
