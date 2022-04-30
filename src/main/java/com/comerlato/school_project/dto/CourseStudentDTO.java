package com.comerlato.school_project.dto;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@With
@Jacksonized
@Builder
public class CourseStudentDTO {

    StudentDTO student;
    List<CourseDTO> courses;
}
