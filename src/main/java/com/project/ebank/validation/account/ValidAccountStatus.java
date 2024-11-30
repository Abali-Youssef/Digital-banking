package com.project.ebank.validation.account;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AccountStatusValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAccountStatus {
    String message() default "Invalid account status. Must be CREATED,ACTIVATED or SUSPENDED.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
