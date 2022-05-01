package com.comerlato.school_project.service;

import com.comerlato.school_project.dto.CourseDTO;
import com.comerlato.school_project.entity.Course;
import com.comerlato.school_project.repository.CourseRepository;
import com.comerlato.school_project.util.helper.MessageHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static com.comerlato.school_project.exception.ErrorCodeEnum.ERROR_COURSE_NOT_FOUND;
import static com.comerlato.school_project.util.mapper.MapperConstants.courseMapper;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@Slf4j
@Service
public class CourseService {

    private final CourseRepository repository;
    private final MessageHelper messageHelper;
    private final InstructorService instructorService;

    public Course findById(final Integer id) {
        return repository.findById(id).orElseThrow(() -> {
            log.error(messageHelper.get(ERROR_COURSE_NOT_FOUND, id));
            throw new ResponseStatusException(NOT_FOUND, messageHelper.get(ERROR_COURSE_NOT_FOUND, id));
        });
    }

    public CourseDTO findDTOById(final Integer id) {
        final var course = findById(id);
        return courseMapper.buildCourseDTO(course)
                .withInstructor(instructorService.findDTOById(course.getInstructorId()));
    }

    public List<CourseDTO> findByInstructorFirstName(final String instructorFirstName) {
        final var courses = repository.findAllByInstructorFirstName(instructorFirstName);
        return courses.stream().map(course -> findDTOById(course.getId())).collect(Collectors.toList());
    }
}
