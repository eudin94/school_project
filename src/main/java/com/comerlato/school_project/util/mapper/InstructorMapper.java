package com.comerlato.school_project.util.mapper;

import com.comerlato.school_project.dto.InstructorCreateRequestDTO;
import com.comerlato.school_project.dto.InstructorDTO;
import com.comerlato.school_project.entity.Instructor;
import org.mapstruct.Mapper;

@Mapper
public interface InstructorMapper {

    Instructor buildInstructor(InstructorCreateRequestDTO request);

    InstructorDTO buildInstructorDTO(Instructor instructor);
}
