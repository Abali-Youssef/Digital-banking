package com.project.ebank.exceptions;

public class BankAccountOperationNotFound extends Throwable {
    public BankAccountOperationNotFound(String operationNotFound) {
        super(operationNotFound);
    }
}
