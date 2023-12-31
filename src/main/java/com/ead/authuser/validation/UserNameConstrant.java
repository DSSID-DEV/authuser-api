package com.ead.authuser.validation;

import com.ead.authuser.validation.constraints.UserNameConstrantImp;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserNameConstrantImp.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserNameConstrant {
    String message() default "Invalid username!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
