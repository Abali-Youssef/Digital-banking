package com.project.ebank.web;

import com.project.ebank.dtos.request.CustomerRequestDTO;
import com.project.ebank.dtos.response.CustomerResponseDTO;
import com.project.ebank.dtos.response.lists.CustomerResponseDTOList;
import com.project.ebank.entities.Customer;
import com.project.ebank.exceptions.CustomerNotFoundException;
import com.project.ebank.service.BankAccountService;
import com.project.ebank.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
public class CustomerRestController {
private CustomerService customerService;
    //@PreAuthorize("hasAuthority('SCOPE_USER')")
    @GetMapping("/customers/{cin}")
    public List<CustomerResponseDTO> getCustomer(@PathVariable(name = "cin") String cin) throws CustomerNotFoundException {
        return customerService.getCustomer(cin);
    }
    @GetMapping("/customers")
    public CustomerResponseDTOList getCustomers(@RequestParam(name = "keyword",defaultValue = "") String keyword, @RequestParam(name = "page",defaultValue = "1") int page, @RequestParam(name = "size",defaultValue = "5") int size){
        return customerService.listCustomer("%"+keyword+"%",page-1,size);
    }
    @PostMapping("/customers/register")
    public CustomerResponseDTO saveCustomer(@RequestBody @Valid CustomerRequestDTO customerDTO) {
    CustomerResponseDTO savedCustomerDTO = customerService.saveCustomer(customerDTO);
    return savedCustomerDTO;
    }
    @PutMapping("/customers/update/{customerId}")
    public CustomerResponseDTO updateCustomer(@PathVariable Long customerId, @RequestBody @Valid CustomerRequestDTO customerDTO) {
    CustomerResponseDTO updatedCustomerDTO = customerService.updateCustomer(customerDTO,customerId);
        return updatedCustomerDTO;
    }
    @DeleteMapping("/customers/delete")
    public void deleteCustomer(@RequestBody List<Long> customerIds){
        customerService.deleteCustomer(customerIds);
    }
}

