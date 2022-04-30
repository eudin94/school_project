package com.comerlato.school_project.repository;

import com.comerlato.school_project.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InstructorRepository extends JpaRepository<Instructor, Integer>, JpaSpecificationExecutor<Instructor> {

}
