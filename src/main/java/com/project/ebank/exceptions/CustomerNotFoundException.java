package com.project.ebank.exceptions;

public class CustomerNotFoundException extends GlobalException {
    public CustomerNotFoundException(String customerNotFound) {
        super(customerNotFound);
    }
}
