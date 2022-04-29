package com.comerlato.school_project.repository;

import com.comerlato.school_project.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
