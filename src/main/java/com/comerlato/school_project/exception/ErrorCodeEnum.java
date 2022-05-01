package com.comerlato.school_project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCodeEnum {

    ERROR_GENERIC_EXCEPTION("error.generic.exception"),
    ERROR_DUPLICATED_FIELD("error.duplicated.field"),
    ERROR_INSTRUCTOR_NOT_FOUND("error.instructor.not.found"),
    ERROR_INSTRUCTOR_DELETION("error.instructor.deletion"),
    ERROR_STUDENT_NOT_FOUND("error.student.not.found"),
    ERROR_STUDENT_DELETION("error.student.deletion"),
    ERROR_STUDENT_ALREADY_ENROLLED("error.student.already.enrolled"),
    ERROR_STUDENT_NOT_ENROLLED("error.student.not.enrolled"),
    ERROR_DEPARTMENT_NOT_FOUND("error.department.not.found"),
    ERROR_COURSE_NOT_FOUND("error.course.not.found");

    private final String messageKey;
}
