package com.comerlato.school_project.util.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class NullableNotBlankConstraint implements ConstraintValidator<NullableNotBlank, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return (!nonNull(s) || !isBlank(s.trim())) && (!nonNull(s) || s.trim().length() <= 255);
    }
}
