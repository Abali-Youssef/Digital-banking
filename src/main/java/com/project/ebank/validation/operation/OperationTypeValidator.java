package com.project.ebank.validation.operation;

import com.project.ebank.enums.CardType;
import com.project.ebank.enums.OperationType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OperationTypeValidator implements ConstraintValidator<ValidOperationType, String> {

    @Override
    public void initialize(ValidOperationType constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String operationType, ConstraintValidatorContext constraintValidatorContext) {
        if(operationType==null){
            return false;
        }

        try {
            OperationType.valueOf(operationType);

            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
