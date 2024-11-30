package com.project.ebank.validation.operation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = OperationTypeValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidOperationType {
    String message() default "Invalid operation type. Must be DEBIT or CREDIT.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
