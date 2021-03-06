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
public class StudentDTO {

    Integer id;
    String firstName;
    String lastName;
    String phone;
    List<CourseDTO> courses;
}
