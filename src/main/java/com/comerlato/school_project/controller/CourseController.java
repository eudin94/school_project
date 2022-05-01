package com.comerlato.school_project.controller;

import com.comerlato.school_project.dto.CourseDTO;
import com.comerlato.school_project.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
@Tag(name = "Course")
public class CourseController {

    private final CourseService service;

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    @Operation(summary = "Find course by id", responses = {@ApiResponse(responseCode = "200")})
    public CourseDTO findDTOById(@PathVariable final Integer id) {
        return service.findDTOById(id);
    }

    @GetMapping("/instructor/{firstName}")
    @ResponseStatus(OK)
    @Operation(summary = "Find course by instructor first name", responses = {@ApiResponse(responseCode = "200")})
    public List<CourseDTO> findDTOById(@PathVariable final String firstName) {
        return service.findByInstructorFirstName(firstName);
    }
}
