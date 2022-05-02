package com.comerlato.school_project.util.creator;

import com.comerlato.school_project.dto.CourseDTO;
import com.comerlato.school_project.entity.Course;

import static com.comerlato.school_project.util.creator.DepartmentCreator.department;
import static com.comerlato.school_project.util.creator.InstructorCreator.instructor;
import static com.comerlato.school_project.util.creator.InstructorCreator.instructorDTO;
import static com.comerlato.school_project.util.factory.PodamFactory.podam;
import static com.comerlato.school_project.util.mapper.MapperConstants.courseMapper;

public class CourseCreator {

    public static final Course course1 = podam.manufacturePojo(Course.class)
            .withDepartmentName(department.getName())
            .withInstructorId(instructor.getId());

    public static final Course course2 = podam.manufacturePojo(Course.class)
            .withDepartmentName(department.getName())
            .withInstructorId(instructor.getId());

    public static final CourseDTO course1DTO = courseMapper.buildCourseDTO(course1)
            .withInstructor(instructorDTO);

    public static final CourseDTO course2DTO = courseMapper.buildCourseDTO(course1)
            .withInstructor(instructorDTO);
}
