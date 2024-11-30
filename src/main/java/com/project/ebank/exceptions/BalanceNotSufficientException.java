package com.project.ebank.exceptions;

public class BalanceNotSufficientException extends GlobalException {
    public BalanceNotSufficientException(String balanceNotSufficient) {
        super(balanceNotSufficient);
    }
}
