package com.comerlato.school_project.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/instructors")
@RequiredArgsConstructor
@Tag(name = "Instructors")
public class InstructorController {
}
