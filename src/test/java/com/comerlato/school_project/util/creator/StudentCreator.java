package com.comerlato.school_project.util.creator;

import com.comerlato.school_project.dto.StudentCreateRequestDTO;
import com.comerlato.school_project.dto.StudentDTO;
import com.comerlato.school_project.dto.StudentUpdateRequestDTO;
import com.comerlato.school_project.entity.Student;

import java.util.List;

import static com.comerlato.school_project.util.creator.CourseCreator.course1;
import static com.comerlato.school_project.util.creator.CourseCreator.course1DTO;
import static com.comerlato.school_project.util.factory.PodamFactory.faker;
import static com.comerlato.school_project.util.factory.PodamFactory.podam;
import static com.comerlato.school_project.util.mapper.MapperConstants.studentMapper;

public class StudentCreator {

    public static final Student student = podam.manufacturePojo(Student.class)
            .withCourses(List.of(course1));

    public static final Student studentToBeSaved = student.withId(null).withCourses(null);

    public static final Student updatedStudent = student.withFirstName(faker.gameOfThrones().dragon());

    public static final StudentDTO studentDTO = studentMapper.buildStudentDTO(student)
            .withCourses(List.of(course1DTO));

    public static final StudentDTO updatedStudentDTO = studentDTO.withFirstName(updatedStudent.getFirstName());

    public static final StudentCreateRequestDTO studentRequest = StudentCreateRequestDTO.builder()
            .firstName(student.getFirstName())
            .lastName(student.getLastName())
            .phone(student.getPhone())
            .build();

    public static final StudentUpdateRequestDTO studentUpdateRequest = StudentUpdateRequestDTO.builder()
            .id(updatedStudent.getId())
            .firstName(updatedStudent.getFirstName())
            .build();
}
