package com.ead.authuser.validation;

import com.ead.authuser.validation.constraints.FullNameConstrantImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FullNameConstrantImpl.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FullNameConstrant {
    String message() default "Full name invalid!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload()  default {};
}
