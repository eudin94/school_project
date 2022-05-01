package com.comerlato.school_project.util.mapper;

import com.comerlato.school_project.dto.CourseDTO;
import com.comerlato.school_project.entity.Course;
import org.mapstruct.Mapper;

@Mapper
public interface CourseMapper {

    CourseDTO buildCourseDTO(Course course);
}
