package com.project.ebank.exceptions;

public class BankAccountOperationNotFound extends GlobalException {
    public BankAccountOperationNotFound(String operationNotFound) {
        super(operationNotFound);
    }
}
