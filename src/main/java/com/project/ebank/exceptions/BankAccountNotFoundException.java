package com.project.ebank.exceptions;

public class BankAccountNotFoundException extends Exception {
    public BankAccountNotFoundException(String bankAccountNotFound){
    super(bankAccountNotFound);
    }
}
