package com.ead.authuser.validation.constraints;

import com.ead.authuser.validation.UserNameConstrant;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserNameConstrantImp implements ConstraintValidator<UserNameConstrant, String> {
    @Override
    public void initialize(UserNameConstrant constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return !(username == null || username.trim().isEmpty() || username.contains(" "));
    }
}
