package com.comerlato.school_project.service;

import com.comerlato.school_project.repository.StudentRepository;
import com.comerlato.school_project.repository.specification.StudentSpecification;
import com.comerlato.school_project.util.helper.MessageHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.comerlato.school_project.exception.ErrorCodeEnum.ERROR_STUDENT_DELETION;
import static com.comerlato.school_project.util.creator.CourseCreator.course1;
import static com.comerlato.school_project.util.creator.CourseCreator.course1DTO;
import static com.comerlato.school_project.util.creator.CourseCreator.course2;
import static com.comerlato.school_project.util.creator.CourseCreator.course2DTO;
import static com.comerlato.school_project.util.creator.StudentCreator.student;
import static com.comerlato.school_project.util.creator.StudentCreator.studentDTO;
import static com.comerlato.school_project.util.creator.StudentCreator.studentRequest;
import static com.comerlato.school_project.util.creator.StudentCreator.studentToBeSaved;
import static com.comerlato.school_project.util.creator.StudentCreator.studentUpdateRequest;
import static com.comerlato.school_project.util.creator.StudentCreator.updatedStudent;
import static com.comerlato.school_project.util.creator.StudentCreator.updatedStudentDTO;
import static com.comerlato.school_project.util.mapper.MapperConstants.studentMapper;
import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(SpringExtension.class)
public class StudentServiceTest {

    @InjectMocks
    private StudentService service;
    @Mock
    private StudentRepository repository;
    @Mock
    private CourseService courseService;
    @Mock
    private MessageHelper messageHelper;

    @Test
    void create_savesStudentAndReturnsDTO_whenSuccessful() {
        when(repository.save(studentToBeSaved)).thenReturn(student);
        when(repository.findById(student.getId())).thenReturn(Optional.of(student));
        when(courseService.findDTOById(course1.getId())).thenReturn(course1DTO);

        final var returnValue = service.create(studentRequest);
        assertEquals(studentDTO, returnValue);
    }

    @Test
    void findById_throws404_whenStudentIsNotFound() {
        when(repository.findById(student.getId())).thenReturn(empty());

        final var responseStatus = assertThrows(ResponseStatusException.class,
                () -> service.findById(student.getId())).getStatus();
        assertEquals(NOT_FOUND, responseStatus);
    }

    @Test
    void findAll_returnsPageOfStudents_whenSuccessful() {
        final var pageable = PageRequest.of(0, 1, Sort.by(ASC, "id"));
        final var page = new PageImpl<>(List.of(student));
        final var assertion = page.map(studentMapper::buildStudentDTO);

        when(repository.findAll(any(StudentSpecification.class), any(Pageable.class))).thenReturn(page);

        final var returnValue = service.findAll(
                empty(), empty(), empty(), pageable
        );
        assertEquals(assertion, returnValue);
    }

    @Test
    void update_changesStudentInfo_whenSuccessful() {
        final var firstCall = new AtomicBoolean(true);

        when(repository.findById(student.getId())).thenAnswer(invocation -> {
            if (firstCall.get()) {
                firstCall.set(false);
                return Optional.of(student);
            }
            return Optional.of(updatedStudent);
        });
        when(repository.save(updatedStudent)).thenReturn(updatedStudent);
        when(courseService.findDTOById(course1.getId())).thenReturn(course1DTO);

        final var returnValue = service.update(studentUpdateRequest);
        assertEquals(updatedStudentDTO, returnValue);
    }

    @Test
    void delete_removesStudent_whenSuccessful() {
        when(repository.findById(student.getId())).thenReturn(Optional.of(student));

        service.delete(student.getId());
        verify(repository, times(1)).delete(student);
    }

    @Test
    void delete_throws400_whenDeletionFails() {
        when(repository.findById(student.getId())).thenReturn(Optional.of(student));
        when(messageHelper.get(ERROR_STUDENT_DELETION, student.getId()))
                .thenReturn("Failed to delete student with id ", student.getId().toString(), ".");
        doThrow(ResponseStatusException.class).when(repository).delete(student);

        final var responseStatus = assertThrows(ResponseStatusException.class,
                () -> service.delete(student.getId())).getStatus();
        assertEquals(BAD_REQUEST, responseStatus);
    }

    @Test
    void enrollStudent_addsCourseToStudent_whenSuccessful() {
        final var enrolledStudent = student.withCourses(List.of(course1, course2));
        final var assertion = studentDTO.withCourses(List.of(course1DTO, course2DTO));
        final var firstCall = new AtomicBoolean(true);

        when(repository.findById(student.getId())).thenAnswer(invocation -> {
            if (firstCall.get()) {
                firstCall.set(false);
                return Optional.of(student);
            }
            return Optional.of(enrolledStudent);
        });
        when(repository.save(enrolledStudent)).thenReturn(enrolledStudent);
        when(courseService.findById(course2.getId())).thenReturn(course2);
        when(courseService.findDTOById(course1.getId())).thenReturn(course1DTO);
        when(courseService.findDTOById(course2.getId())).thenReturn(course2DTO);

        final var returnValue = service.enrollStudent(student.getId(), course2.getId());
        assertEquals(assertion, returnValue);
    }

    @Test
    void enrollStudent_throws400_whenStudentIsAlreadyEnrolled() {
        when(repository.findById(student.getId())).thenReturn(Optional.of(student));
        when(courseService.findById(course1.getId())).thenReturn(course1);

        final var responseStatus = assertThrows(ResponseStatusException.class,
                () -> service.enrollStudent(student.getId(), course1.getId())).getStatus();
        assertEquals(BAD_REQUEST, responseStatus);
    }

    @Test
    void unenrollStudent_removesCourseFromStudent_whenSuccessful() {
        final var unenrolledStudent = student.withCourses(List.of());
        final var assertion = studentDTO.withCourses(List.of());
        final var firstCall = new AtomicBoolean(true);

        when(repository.findById(student.getId())).thenAnswer(invocation -> {
            if (firstCall.get()) {
                firstCall.set(false);
                return Optional.of(student);
            }
            return Optional.of(unenrolledStudent);
        });
        when(repository.save(unenrolledStudent)).thenReturn(unenrolledStudent);
        when(courseService.findById(course1.getId())).thenReturn(course1);

        final var returnValue = service.unenrollStudent(student.getId(), course1.getId());
        assertEquals(assertion, returnValue);
    }

    @Test
    void unenrollStudent_throws400_whenStudentIsNotEnrolled() {
        when(repository.findById(student.getId())).thenReturn(Optional.of(student));
        when(courseService.findById(course2.getId())).thenReturn(course2);

        final var responseStatus = assertThrows(ResponseStatusException.class,
                () -> service.unenrollStudent(student.getId(), course2.getId())).getStatus();
        assertEquals(BAD_REQUEST, responseStatus);
    }
}
