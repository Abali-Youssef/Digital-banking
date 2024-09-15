package com.project.ebank.service;

import com.project.ebank.entities.BankAccount;
import com.project.ebank.entities.SavingAccount;
import com.project.ebank.repositories.BankAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class BankService {
    BankAccountRepository bankAccountRepository;
    public void consulter(){
        BankAccount bankAccount= bankAccountRepository.findById("31aa6515-a9bc-43b1-bc5c-884250cc1fd5").orElse(null);
        System.out.println("******************************");
        System.out.println(bankAccount.getBalance());
        System.out.println(bankAccount.getCreatedAt());
        System.out.println(((SavingAccount) bankAccount).getInterestRate() );
        bankAccount.getBankAccountOperations().forEach(op ->{
            System.out.println(op.getAmount());
        });
    }
}
