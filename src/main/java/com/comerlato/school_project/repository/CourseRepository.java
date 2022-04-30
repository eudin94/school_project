package com.comerlato.school_project.repository;

import com.comerlato.school_project.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {

}
