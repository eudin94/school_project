package com.comerlato.school_project.service;

import com.comerlato.school_project.dto.StudentCreateRequestDTO;
import com.comerlato.school_project.dto.StudentDTO;
import com.comerlato.school_project.dto.StudentUpdateRequestDTO;
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

import java.util.Optional;

import static com.comerlato.school_project.exception.ErrorCodeEnum.ERROR_STUDENT_DELETION;
import static com.comerlato.school_project.exception.ErrorCodeEnum.ERROR_STUDENT_NOT_FOUND;
import static com.comerlato.school_project.util.mapper.MapperConstants.studentMapper;
import static java.lang.String.format;
import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@Slf4j
@Service
public class StudentService {

    private final StudentRepository repository;
    private final MessageHelper messageHelper;

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
        return studentMapper.buildStudentDTO(findById(id));
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
                .withFirstName(isNull(request.getFirstName()) ? student.getFirstName()
                        : request.getFirstName())
                .withLastName(isNull(request.getLastName()) ? student.getLastName()
                        : request.getLastName())
                .withPhone(isNull(request.getPhone()) ? student.getPhone()
                        : request.getPhone())
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
}
