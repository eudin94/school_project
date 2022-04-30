package com.comerlato.school_project.dto;

import com.comerlato.school_project.util.validation.Phone;
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

    @Phone
    @NotBlank
    @Length(min = 1, max = 255)
    String phone;
}
