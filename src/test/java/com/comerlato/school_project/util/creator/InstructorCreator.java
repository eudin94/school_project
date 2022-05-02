package com.comerlato.school_project.util.creator;

import com.comerlato.school_project.dto.InstructorDTO;
import com.comerlato.school_project.entity.Instructor;

import static com.comerlato.school_project.util.creator.DepartmentCreator.department;
import static com.comerlato.school_project.util.factory.PodamFactory.podam;
import static com.comerlato.school_project.util.mapper.MapperConstants.instructorMapper;

public class InstructorCreator {

    public static final Instructor instructor = podam.manufacturePojo(Instructor.class)
            .withDepartmentName(department.getName());

    public static final InstructorDTO instructorDTO = instructorMapper.buildInstructorDTO(instructor);
}
