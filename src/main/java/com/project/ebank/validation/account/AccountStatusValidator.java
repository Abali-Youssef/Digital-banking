package com.project.ebank.validation.account;

import com.project.ebank.enums.AccountStatus;
import com.project.ebank.enums.CardType;
import com.project.ebank.validation.card.ValidCardType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AccountStatusValidator implements ConstraintValidator<ValidAccountStatus, String> {

    @Override
    public void initialize(ValidAccountStatus constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String accountStatus, ConstraintValidatorContext constraintValidatorContext) {
        if(accountStatus==null){
            return false;
        }
        try {
            AccountStatus.valueOf(accountStatus);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
