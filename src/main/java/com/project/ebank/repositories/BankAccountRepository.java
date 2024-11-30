package com.project.ebank.repositories;

import com.project.ebank.entities.BankAccount;
import com.project.ebank.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
    Page<BankAccount> findByCustomerCin(String cin, Pageable pageable);
    List<BankAccount> findByCustomerCin(String cin);


}