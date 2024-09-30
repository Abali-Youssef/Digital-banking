package com.project.ebank;

import com.project.ebank.dtos.CardDTO;
import com.project.ebank.dtos.CurrentAccountDTO;
import com.project.ebank.dtos.CustomerDTO;
import com.project.ebank.dtos.SavingAccountDTO;
import com.project.ebank.entities.CurrentAccount;
import com.project.ebank.entities.Customer;
import com.project.ebank.enums.CardType;
import com.project.ebank.exceptions.BalanceNotSufficientException;
import com.project.ebank.exceptions.BankAccountNotFoundException;
import com.project.ebank.exceptions.CustomerNotFoundException;
import com.project.ebank.service.BankAccountService;
import com.project.ebank.service.CustomerService;
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
	CommandLineRunner start(BankAccountService bankAccountService, CustomerService customerService){
		return args -> {
			Stream.of("hannae","youssef","houda").forEach(name ->{
				CustomerDTO customer=new CustomerDTO();
				customer.setName(name);
				customer.setEmail(name+"@gmail.com");
				customerService.saveCustomer(customer);
			});
			customerService.listCustomer().forEach(cust ->{
				for (int i = 0; i < 8; i++) {
					try {

						CurrentAccountDTO currentAccountDTO =bankAccountService.saveCurrentBankAccount(Math.random()*333,4444,cust.getId());
						SavingAccountDTO savingAccountDTO=bankAccountService.saveSavingBankAccount(Math.random()*113,4,cust.getId());
						bankAccountService.attachCard(currentAccountDTO.getId(),new CardDTO(UUID.randomUUID().toString(),new Date(), CardType.DEBIT_CARD));
						bankAccountService.attachCard(savingAccountDTO.getId(),new CardDTO(UUID.randomUUID().toString(),new Date(), CardType.CREDIT_CARD));

					} catch (CustomerNotFoundException | BankAccountNotFoundException e) {
						throw new RuntimeException(e);
					}
				}


			});
				bankAccountService.listBankAccounts().forEach(acc -> {

                    try {
						bankAccountService.credit(acc.getId(),2235,"credit");
						bankAccountService.debit(acc.getId(),225,"debit");
                    } catch (BankAccountNotFoundException | BalanceNotSufficientException e) {
                        throw new RuntimeException(e);
                    }

                });




		};

}
}
