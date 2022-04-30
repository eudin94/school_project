package com.comerlato.school_project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCodeEnum {

    ERROR_GENERIC_EXCEPTION("error.generic.exception"),
    ERROR_DUPLICATED_FIELD("error.duplicated.field"),
    ERROR_INSTRUCTOR_NOT_FOUND("error.instructor.not.found"),
    ERROR_INSTRUCTOR_DELETION("error.instructor.deletion");

    private final String messageKey;
}
