package com.comerlato.school_project.util.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.util.Objects.isNull;

public class PhoneConstraint implements ConstraintValidator<Phone, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (isNull(s)) return true;

        s = s.trim();
        s = s.replace("(", "");
        s = s.replace(")", "");

        if (s.length() < 10) return false;

        return s.matches("^(1[ \\-\\+]{0,3}|\\+1[ -\\+]{0,3}|\\+1|\\+)?((\\(\\+?1-[2-9][0-9]{1,2}\\))|(\\(\\+?[2-8][0-9][0-9]\\))|(\\(\\+?[1-9][0-9]\\))|(\\(\\+?[17]\\))|([ \\-\\.]{0,3}[0-9]{2,4}))?([ \\-\\.][0-9])?([ \\-\\.]{0,3}[0-9]{2,4}){2,3}$");
    }

}
