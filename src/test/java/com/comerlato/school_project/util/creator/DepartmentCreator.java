package com.comerlato.school_project.util.creator;

import com.comerlato.school_project.entity.Department;

import static com.comerlato.school_project.util.factory.PodamFactory.podam;

public class DepartmentCreator {

    public static final Department department = podam.manufacturePojo(Department.class);
}
