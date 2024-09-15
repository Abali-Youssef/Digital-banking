package com.project.ebank.repositories;

import com.project.ebank.entities.BankAccount;
import com.project.ebank.entities.BankAccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountOperationRepository extends JpaRepository<BankAccountOperation,Long> {
}
