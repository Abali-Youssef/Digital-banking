package com.project.ebank;

import com.project.ebank.entities.*;
import com.project.ebank.enums.AccountStatus;
import com.project.ebank.enums.OperationType;
import com.project.ebank.exceptions.BalanceNotSufficientException;
import com.project.ebank.exceptions.BankAccountNotFoundException;
import com.project.ebank.exceptions.CustomerNotFoundException;
import com.project.ebank.repositories.BankAccountOperationRepository;
import com.project.ebank.repositories.BankAccountRepository;
import com.project.ebank.repositories.CustomerRepository;
import com.project.ebank.service.BankAccountService;
import com.project.ebank.service.BankService;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbankingBackApplication.class, args);
	}
@Bean
	CommandLineRunner start(BankAccountService bankAccountService){
		return args -> {
			Stream.of("hannae","youssef","houda").forEach(name ->{
				Customer customer=new Customer();
				customer.setName(name);
				customer.setEmail(name+"@gmail.com");
				bankAccountService.saveCustomer(customer);
				bankAccountService.listCustomer().forEach(cust ->{
                    try {
                        bankAccountService.saveCurrentBankAccount(Math.random()*333,4444,cust.getId());
						bankAccountService.saveSavingBankAccount(Math.random()*113,4,cust.getId());
					} catch (CustomerNotFoundException e) {
                        throw new RuntimeException(e);
                    }

				});
				bankAccountService.listBankAccounts().forEach(acc -> {
                    try {
						bankAccountService.credit(acc.getId(),2235,"credit");
						bankAccountService.debit(acc.getId(),2235,"debit");
                    } catch (BankAccountNotFoundException | BalanceNotSufficientException e) {
                        throw new RuntimeException(e);
                    }

                });

			});


		};

}
}
