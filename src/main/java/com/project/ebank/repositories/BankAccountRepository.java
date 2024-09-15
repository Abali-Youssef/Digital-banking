package com.project.ebank.repositories;

import com.project.ebank.entities.BankAccount;
import com.project.ebank.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
