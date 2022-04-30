package com.comerlato.school_project.dto;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import lombok.extern.jackson.Jacksonized;

@Value
@With
@Jacksonized
@Builder
public class CourseDTO {

    Integer id;
    String departmentName;
    InstructorDTO instructor;
    Integer duration;
    String name;
}
