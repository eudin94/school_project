package com.comerlato.school_project.dto;

import com.comerlato.school_project.util.validation.NullableNotBlank;
import com.comerlato.school_project.util.validation.Phone;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Value
@With
@Jacksonized
@Builder
public class InstructorUpdateRequestDTO {

    @Positive
    @NotNull
    Integer id;

    @NullableNotBlank
    String departmentName;

    @NullableNotBlank
    String headedBy;

    @NullableNotBlank
    String firstName;

    @NullableNotBlank
    String lastName;

    @Phone
    @NullableNotBlank
    String phone;
}
