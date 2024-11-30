package com.project.ebank.exceptions;

public class BankAccountNotFoundException extends GlobalException {
    public BankAccountNotFoundException(String bankAccountNotFound){
    super(bankAccountNotFound);
    }
}
