package com.comerlato.school_project.dto;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Value
@With
@Jacksonized
@Builder
public class InstructorCreateRequestDTO {

    @NotBlank
    @Length(min = 1, max = 255)
    String departmentName;

    @NotBlank
    @Length(min = 1, max = 255)
    String headedBy;

    @NotBlank
    @Length(min = 1, max = 255)
    String firstName;

    @NotBlank
    @Length(min = 1, max = 255)
    String lastName;

    @NotBlank
    @Length(min = 1, max = 255)
    String phone;
}
