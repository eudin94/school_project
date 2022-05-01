package com.comerlato.school_project.repository;

import com.comerlato.school_project.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer> {

    @Query(value = "SELECT c FROM Course c INNER JOIN Instructor i ON c.instructorId IN i.id AND i.firstName IN ?1")
    List<Course> findAllByInstructorFirstName(final String instructorFirstName);
}
