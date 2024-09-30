package com.project.ebank.repositories;

import com.project.ebank.entities.BankAccount;
import com.project.ebank.entities.BankAccountOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankAccountOperationRepository extends JpaRepository<BankAccountOperation,Long> {
    List<BankAccountOperation> findByBankAccountId(String accountId);
    Page<BankAccountOperation> findByBankAccountId(String accountId, Pageable pageable);
}
