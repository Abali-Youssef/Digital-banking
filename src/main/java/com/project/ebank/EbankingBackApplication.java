package com.project.ebank;


import com.project.ebank.dtos.request.CardRequestDTO;
import com.project.ebank.dtos.request.CustomerRequestDTO;
import com.project.ebank.dtos.response.CardResponseDTO;
import com.project.ebank.dtos.response.CurrentAccountResponseDTO;
import com.project.ebank.dtos.response.SavingAccountResponseDTO;

import com.project.ebank.entities.Permission;
import com.project.ebank.enums.CardType;
import com.project.ebank.exceptions.BalanceNotSufficientException;
import com.project.ebank.exceptions.BankAccountNotFoundException;
import com.project.ebank.exceptions.CustomerNotFoundException;
import com.project.ebank.repositories.PermissionRepository;
import com.project.ebank.security.RsakeysConfig;
import com.project.ebank.service.BankAccountService;
import com.project.ebank.service.CardService;
import com.project.ebank.service.CustomerService;
import com.project.ebank.service.SecurityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
@EnableConfigurationProperties(RsakeysConfig.class)
public class EbankingBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbankingBackApplication.class, args);
	}
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

//@Bean
	CommandLineRunner start(PermissionRepository permissionRepository, SecurityService securityService, BankAccountService bankAccountService, CustomerService customerService, CardService cardService){
		return args -> {
			Stream.of("EDIT_","ADD_","DELETE_","VIEW_").forEach(prm ->{
				Stream.of("ACCOUNT","CARD","CUSTOMER","ROLE","OPERATION").forEach(module ->{
					securityService.addPermission(prm+module);
				});
			});
			securityService.saveRole(  "user",  "user role");
			securityService.saveRole(  "admin",  "admin role");
			permissionRepository.findAll().forEach(p -> {
				securityService.addPermissionToRole(p.getName(), "admin");
				securityService.addPermissionToRole(p.getName(), "user");

			});
//			Stream.of("ADD_","VIEW_").forEach(prm ->{
//				Stream.of("OPERATION").forEach(module ->{
//					securityService.removePermissionFromRole(prm+module,"user");
//				});
//			});
			Stream.of("hannae","youssef","houda").forEach(name ->{
				CustomerRequestDTO customer=new CustomerRequestDTO();
				customer.setName(name);
				customer.setCin(name.toUpperCase().substring(0,2)+String.valueOf((int)Math.random()*1000));
				customer.setEmail(name+"@gmail.com");
				customer.setPassword("aaa");
				customerService.saveCustomer(customer);
			});
			customerService.listCustomer("",0,50).getCustomers().forEach(cust ->{
				for (int i = 0; i < 8; i++) {
					try {
						CurrentAccountResponseDTO currentAccountDTO =bankAccountService.saveCurrentBankAccount(Math.random()*333,4444,cust.getId());
						SavingAccountResponseDTO savingAccountDTO=bankAccountService.saveSavingBankAccount(Math.random()*113,4,cust.getId());
						CardResponseDTO savedCard=cardService.saveCard(new CardRequestDTO(new Date(),String.valueOf(CardType.DEBIT_CARD) ,currentAccountDTO.getId()));
						//bankAccountService.attachCard(currentAccountDTO.getId(),savedCard.getId());
					} catch (CustomerNotFoundException | BankAccountNotFoundException ex) {
						throw new RuntimeException(ex);
					}
                }
			});
				bankAccountService.listBankAccounts("",0,100).getBankAccounts().forEach(acc -> {

                    try {
						bankAccountService.credit(acc.getId(),2235,"credit");
						bankAccountService.debit(acc.getId(),225,"debit");
                    } catch (BankAccountNotFoundException | BalanceNotSufficientException e) {
                        throw new RuntimeException(e);
                    }

                });

			Stream.of("morad","alex","houssam").forEach(name ->{
				CustomerRequestDTO customer=new CustomerRequestDTO();
				customer.setName(name);
				customer.setCin(name.toUpperCase().substring(0,2)+String.valueOf((int)Math.random()*1000));
				customer.setEmail(name+"@gmail.com");
				customer.setPassword("aaa");
				customerService.saveCustomer(customer);
			});


		};

}
}
