package com.project.ebank.validation.card;

import com.project.ebank.entities.Card;
import com.project.ebank.enums.CardType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CardTypeValidator implements ConstraintValidator<ValidCardType, String> {

    @Override
    public void initialize(ValidCardType constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String cardType, ConstraintValidatorContext constraintValidatorContext) {
if(cardType==null){
    return false;
}
        try {
            CardType.valueOf(cardType);

            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }

    }
}
