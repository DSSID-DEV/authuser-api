package com.ead.authuser.validation.constraints;

import com.ead.authuser.validation.FullNameConstrant;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FullNameConstrantImpl implements ConstraintValidator<FullNameConstrant, String> {
    @Override
    public void initialize(FullNameConstrant constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String fullname, ConstraintValidatorContext constraintValidatorContext) {
        return fullname.split(" ").length > 1;
    }
}
