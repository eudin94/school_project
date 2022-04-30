package com.comerlato.school_project.util.mapper;

import org.mapstruct.factory.Mappers;

public class MapperConstants {

    private MapperConstants() {
    }

    public static final InstructorMapper instructorMapper = Mappers.getMapper(InstructorMapper.class);

}
