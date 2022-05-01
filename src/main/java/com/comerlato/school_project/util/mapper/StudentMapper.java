package com.comerlato.school_project.util.mapper;

import com.comerlato.school_project.dto.StudentCreateRequestDTO;
import com.comerlato.school_project.dto.StudentDTO;
import com.comerlato.school_project.entity.Student;
import org.mapstruct.Mapper;

@Mapper
public interface StudentMapper {

    Student buildStudent(StudentCreateRequestDTO request);

    StudentDTO buildStudentDTO(Student instructor);
}
