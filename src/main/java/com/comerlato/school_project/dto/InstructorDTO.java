package com.comerlato.school_project.dto;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import lombok.extern.jackson.Jacksonized;

@Value
@With
@Jacksonized
@Builder
public class InstructorDTO {

    Integer id;
    String departmentName;
    String headedBy;
    String firstName;
    String lastName;
    String phone;
}
