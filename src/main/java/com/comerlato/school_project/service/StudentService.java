package com.comerlato.school_project.service;

import com.comerlato.school_project.dto.StudentCreateRequestDTO;
import com.comerlato.school_project.dto.StudentDTO;
import com.comerlato.school_project.dto.StudentUpdateRequestDTO;
import com.comerlato.school_project.entity.Course;
import com.comerlato.school_project.entity.Student;
import com.comerlato.school_project.repository.StudentRepository;
import com.comerlato.school_project.repository.specification.StudentSpecification;
import com.comerlato.school_project.util.helper.MessageHelper;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.comerlato.school_project.exception.ErrorCodeEnum.ERROR_STUDENT_ALREADY_ENROLLED;
import static com.comerlato.school_project.exception.ErrorCodeEnum.ERROR_STUDENT_DELETION;
import static com.comerlato.school_project.exception.ErrorCodeEnum.ERROR_STUDENT_NOT_ENROLLED;
import static com.comerlato.school_project.exception.ErrorCodeEnum.ERROR_STUDENT_NOT_FOUND;
import static com.comerlato.school_project.util.mapper.MapperConstants.studentMapper;
import static java.lang.String.format;
import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.util.CollectionUtils.isEmpty;

@RequiredArgsConstructor
@Slf4j
@Service
public class StudentService {

    private final StudentRepository repository;
    private final MessageHelper messageHelper;
    private final CourseService courseService;

    public StudentDTO create(final StudentCreateRequestDTO request) {
        final var savedStudent = repository.save(studentMapper.buildStudent(request));
        return findDTOById(savedStudent.getId());
    }

    public Student findById(final Integer id) {
        return repository.findById(id).orElseThrow(() -> {
            log.error(messageHelper.get(ERROR_STUDENT_NOT_FOUND, id));
            throw new ResponseStatusException(NOT_FOUND, messageHelper.get(ERROR_STUDENT_NOT_FOUND, id));
        });
    }

    public StudentDTO findDTOById(final Integer id) {
        final var student = findById(id);
        return studentMapper.buildStudentDTO(student)
                .withCourses(
                        student.getCourses().stream().map(
                                course -> courseService.findDTOById(course.getId())
                        ).collect(Collectors.toList())
                );
    }

    public Page<StudentDTO> findAll(final Optional<String> firstName,
                                    final Optional<String> lastName,
                                    final Optional<String> phone,
                                    final Pageable pageable) {
        return repository.findAll(StudentSpecification.builder()
                .firstName(firstName)
                .lastName(lastName)
                .phone(phone)
                .build(), pageable).map(studentMapper::buildStudentDTO);
    }

    public StudentDTO update(final StudentUpdateRequestDTO request) {
        final var student = findById(request.getId());
        final var updatedStudent = repository.save(student
                .withFirstName(isNull(request.getFirstName()) ? student.getFirstName() : request.getFirstName())
                .withLastName(isNull(request.getLastName()) ? student.getLastName() : request.getLastName())
                .withPhone(isNull(request.getPhone()) ? student.getPhone() : request.getPhone())
        );
        return findDTOById(updatedStudent.getId());
    }

    public void delete(final Integer id) {
        final var student = findById(id);
        Try.run(() -> repository.delete(student)).onFailure(throwable -> {
            log.error(messageHelper.get(ERROR_STUDENT_DELETION, id), throwable.getMessage());
            throw new ResponseStatusException(
                    BAD_REQUEST, format(messageHelper.get(ERROR_STUDENT_DELETION, id), throwable.getMessage())
            );
        });
    }

    public StudentDTO enrollStudent(final Integer studentId, final Integer courseId) {
        final var student = findById(studentId);
        final var course = courseService.findById(courseId);
        final var updatedCourses = addCourseToStudentList(student.getCourses(), course);
        final var updatedStudent = repository.save(student.withCourses(updatedCourses));
        return findDTOById(updatedStudent.getId());
    }

    public StudentDTO unenrollStudent(final Integer studentId, final Integer courseId) {
        final var student = findById(studentId);
        final var course = courseService.findById(courseId);
        final var updatedCourses = removeCourseFromStudentList(student.getCourses(), course);
        final var updatedStudent = repository.save(student.withCourses(updatedCourses));
        return findDTOById(updatedStudent.getId());
    }

    private List<Course> addCourseToStudentList(final List<Course> studentCourses, final Course course) {
        if (isEmpty(studentCourses)) return List.of(course);
        if (studentCourses.contains(course)) {
            final var errorMessage = messageHelper.get(ERROR_STUDENT_ALREADY_ENROLLED, course.getId());
            log.error(errorMessage);
            throw new ResponseStatusException(BAD_REQUEST, errorMessage);
        }
        studentCourses.add(course);
        return studentCourses;
    }

    private List<Course> removeCourseFromStudentList(final List<Course> studentCourses, final Course course) {
        if (isEmpty(studentCourses) || !studentCourses.contains(course)) {
            final var errorMessage = messageHelper.get(ERROR_STUDENT_NOT_ENROLLED, course.getId());
            log.error(errorMessage);
            throw new ResponseStatusException(BAD_REQUEST, errorMessage);
        }
        studentCourses.remove(course);
        return studentCourses;
    }
}
