package com.project.ebank.validation.card;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CardTypeValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCardType {
    String message() default "Invalid card type. Must be DEBIT_CARD or CREDIT_CARD.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
