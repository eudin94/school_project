package com.comerlato.school_project.controller;

import com.comerlato.school_project.dto.StudentCreateRequestDTO;
import com.comerlato.school_project.dto.StudentDTO;
import com.comerlato.school_project.dto.StudentUpdateRequestDTO;
import com.comerlato.school_project.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
@Tag(name = "Students")
public class StudentController {

    private final StudentService service;

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Create student", responses = {@ApiResponse(responseCode = "201")})
    public StudentDTO create(@Valid @RequestBody final StudentCreateRequestDTO request) {
        return service.create(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    @Operation(summary = "Find student by id", responses = {@ApiResponse(responseCode = "200")})
    public StudentDTO findDTOById(@PathVariable final Integer id) {
        return service.findDTOById(id);
    }

    @GetMapping
    @ResponseStatus(OK)
    @Operation(summary = "Find all students", responses = {@ApiResponse(responseCode = "200")})
    public Page<StudentDTO> findAll(@RequestParam(required = false) Optional<String> firstName,
                                    @RequestParam(required = false) Optional<String> lastName,
                                    @RequestParam(required = false) Optional<String> phone,
                                    @RequestParam(defaultValue = "0") final Integer page,
                                    @RequestParam(defaultValue = "10") final Integer size,
                                    @RequestParam(defaultValue = "id") final String sort,
                                    @RequestParam(defaultValue = "ASC") final Sort.Direction direction) {
        final var pageable = PageRequest.of(page, size, Sort.by(direction, sort));
        return service.findAll(firstName, lastName, phone, pageable);
    }

    @PutMapping
    @ResponseStatus(OK)
    @Operation(summary = "Update student", responses = {@ApiResponse(responseCode = "200")})
    public StudentDTO update(@Valid @RequestBody final StudentUpdateRequestDTO request) {
        return service.update(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Delete student by id", responses = {@ApiResponse(responseCode = "204")})
    public void delete(@PathVariable final Integer id) {
        service.delete(id);
    }

    @PutMapping("/{studentId}/enroll/{courseId}")
    @ResponseStatus(OK)
    @Operation(summary = "Enroll student in course by id", responses = {@ApiResponse(responseCode = "200")})
    public StudentDTO enrollStudent(@PathVariable final Integer studentId,
                                    @PathVariable final Integer courseId) {
        return service.enrollStudent(studentId, courseId);
    }

    @PutMapping("/{studentId}/unenroll/{courseId}")
    @ResponseStatus(OK)
    @Operation(summary = "Unenroll student from course by id", responses = {@ApiResponse(responseCode = "200")})
    public StudentDTO unenrollStudent(@PathVariable final Integer studentId,
                                      @PathVariable final Integer courseId) {
        return service.unenrollStudent(studentId, courseId);
    }
}