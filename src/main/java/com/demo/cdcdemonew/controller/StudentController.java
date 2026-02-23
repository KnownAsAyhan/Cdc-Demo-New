package com.demo.cdcdemonew.controller;

import com.demo.cdcdemonew.service.StudentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/batch")
    public String insertBatch(@RequestParam int count) {

        long start = System.currentTimeMillis();

        studentService.batchInsertStudents(count);

        long end = System.currentTimeMillis();

        return "Inserted " + count + " students in " + (end - start) + " ms";
    }
}