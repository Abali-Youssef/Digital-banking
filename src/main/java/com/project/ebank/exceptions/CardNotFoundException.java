package com.project.ebank.exceptions;

public class CardNotFoundException extends GlobalException {
    public CardNotFoundException(String cardNotFound) {
        super(cardNotFound);
    }
}
