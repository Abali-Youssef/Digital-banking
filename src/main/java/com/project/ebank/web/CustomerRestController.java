package com.project.ebank.web;

import com.project.ebank.dtos.CustomerDTO;
import com.project.ebank.entities.Customer;
import com.project.ebank.exceptions.CustomerNotFoundException;
import com.project.ebank.service.BankAccountService;
import com.project.ebank.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
public class CustomerRestController {
private CustomerService customerService;
@GetMapping("/customers")
public  List<CustomerDTO> customers(){
    return customerService.listCustomer();
    }
    @GetMapping("/customers/{id}")
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
        return customerService.getCustomer(customerId);
    }
    @GetMapping("/customers/search")
    public List<CustomerDTO> searchCustomer(@RequestParam(name = "keyword",defaultValue = "") String keyword){
        return customerService.searchCustomer("%"+keyword+"%");
    }
    @PostMapping("/customers")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
    CustomerDTO savedCustomerDTO = customerService.saveCustomer(customerDTO);
    return savedCustomerDTO;
    }
    @PutMapping("/customers/update/{customerId}")
    public CustomerDTO updateCustomer(@PathVariable Long customerId, @RequestBody CustomerDTO customerDTO) {
    customerDTO.setId(customerId);
    CustomerDTO updatedCustomerDTO = customerService.saveCustomer(customerDTO);
        return updatedCustomerDTO;
    }
    @DeleteMapping("/customers/delete/{customerId}")
    public void deleteCustomer(@PathVariable Long customerId){
        customerService.deleteCustomer(customerId);
    }
}

