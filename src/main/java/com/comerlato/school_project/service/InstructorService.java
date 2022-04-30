package com.comerlato.school_project.service;

import com.comerlato.school_project.dto.InstructorCreateRequestDTO;
import com.comerlato.school_project.dto.InstructorDTO;
import com.comerlato.school_project.dto.InstructorUpdateRequestDTO;
import com.comerlato.school_project.entity.Instructor;
import com.comerlato.school_project.repository.InstructorRepository;
import com.comerlato.school_project.repository.specification.InstructorSpecification;
import com.comerlato.school_project.util.helper.MessageHelper;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static com.comerlato.school_project.exception.ErrorCodeEnum.ERROR_INSTRUCTOR_DELETION;
import static com.comerlato.school_project.exception.ErrorCodeEnum.ERROR_INSTRUCTOR_NOT_FOUND;
import static com.comerlato.school_project.util.mapper.MapperConstants.instructorMapper;
import static java.lang.String.format;
import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@Slf4j
@Service
public class InstructorService {

    private final InstructorRepository repository;
    private final MessageHelper messageHelper;

    public InstructorDTO create(final InstructorCreateRequestDTO request) {
        final var savedInstructor = repository.save(instructorMapper.buildInstructor(request));
        return findDTOById(savedInstructor.getId());
    }

    public Instructor findById(final Integer id) {
        return repository.findById(id).orElseThrow(() -> {
            log.error(messageHelper.get(ERROR_INSTRUCTOR_NOT_FOUND, id));
            throw new ResponseStatusException(NOT_FOUND, messageHelper.get(ERROR_INSTRUCTOR_NOT_FOUND, id));
        });
    }

    public InstructorDTO findDTOById(final Integer id) {
        return instructorMapper.buildInstructorDTO(findById(id));
    }

    public Page<InstructorDTO> findAll(final Optional<String> departmentName,
                                       final Optional<String> headedBy,
                                       final Optional<String> firstName,
                                       final Optional<String> lastName,
                                       final Optional<String> phone,
                                       final Pageable pageable) {
        return repository.findAll(InstructorSpecification.builder()
                .departmentName(departmentName)
                .headedBy(headedBy)
                .firstName(firstName)
                .lastName(lastName)
                .phone(phone)
                .build(), pageable).map(instructorMapper::buildInstructorDTO);
    }

    public InstructorDTO update(final InstructorUpdateRequestDTO request) {
        final var instructor = findById(request.getId());
        final var updatedInstructor = repository.save(instructor
                .withDepartmentName(isNull(request.getDepartmentName()) ? instructor.getDepartmentName()
                        : request.getDepartmentName())
                .withHeadedBy(isNull(request.getHeadedBy()) ? instructor.getHeadedBy()
                        : request.getHeadedBy())
                .withFirstName(isNull(request.getFirstName()) ? instructor.getFirstName()
                        : request.getFirstName())
                .withLastName(isNull(request.getLastName()) ? instructor.getLastName()
                        : request.getLastName())
                .withPhone(isNull(request.getPhone()) ? instructor.getPhone()
                        : request.getPhone())
        );
        return findDTOById(updatedInstructor.getId());
    }

    public void delete(final Integer id) {
        final var instructor = findById(id);
        Try.run(() -> repository.delete(instructor)).onFailure(throwable -> {
            log.error(messageHelper.get(ERROR_INSTRUCTOR_DELETION, id), throwable.getMessage());
            throw new ResponseStatusException(
                    BAD_REQUEST, format(messageHelper.get(ERROR_INSTRUCTOR_DELETION, id), throwable.getMessage())
            );
        });
    }
}
