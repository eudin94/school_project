package com.comerlato.school_project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Builder
@With
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "instructor")
public class Instructor {

    @Id
    private Integer id;
    private String departmentName;
    private String headedBy;
    private String firstName;
    private String lastName;
    private String phone;
}
