package com.comerlato.school_project.controller;

import com.comerlato.school_project.dto.InstructorCreateRequestDTO;
import com.comerlato.school_project.dto.InstructorDTO;
import com.comerlato.school_project.dto.InstructorUpdateRequestDTO;
import com.comerlato.school_project.service.InstructorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
@RequestMapping("/instructors")
@RequiredArgsConstructor
@Tag(name = "Instructors")
public class InstructorController {

    private final InstructorService service;

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Create instructor", responses = {@ApiResponse(responseCode = "201")})
    public InstructorDTO create(@Valid @RequestBody final InstructorCreateRequestDTO request) {
        return service.create(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    @Operation(summary = "Find instructor by id", responses = {@ApiResponse(responseCode = "200")})
    public InstructorDTO findDTOById(@PathVariable final Integer id) {
        return service.findDTOById(id);
    }

    @GetMapping
    @ResponseStatus(OK)
    @Operation(summary = "Find all instructors", responses = {@ApiResponse(responseCode = "200")})
    public Page<InstructorDTO> findAll(@RequestParam(required = false) Optional<String> departmentName,
                                       @RequestParam(required = false) Optional<String> headedBy,
                                       @RequestParam(required = false) Optional<String> firstName,
                                       @RequestParam(required = false) Optional<String> lastName,
                                       @RequestParam(required = false) Optional<String> phone,
                                       @RequestParam(defaultValue = "0") final Integer page,
                                       @RequestParam(defaultValue = "10") final Integer size,
                                       @RequestParam(defaultValue = "id") final String sort,
                                       @RequestParam(defaultValue = "ASC") final Sort.Direction direction) {
        final var pageable = PageRequest.of(page, size, Sort.by(direction, sort));
        return service.findAll(departmentName, headedBy, firstName, lastName, phone, pageable);
    }

    @PutMapping
    @ResponseStatus(OK)
    @Operation(summary = "Update instructor", responses = {@ApiResponse(responseCode = "200")})
    public InstructorDTO update(@Valid @RequestBody final InstructorUpdateRequestDTO request) {
        return service.update(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Delete instructor by id", responses = {@ApiResponse(responseCode = "204")})
    public void delete(@PathVariable final Integer id) {
        service.delete(id);
    }
}
