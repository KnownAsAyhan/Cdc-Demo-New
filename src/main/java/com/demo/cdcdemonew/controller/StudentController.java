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

    @GetMapping("/one-by-one")
    public String insertOneByOne(@RequestParam(defaultValue = "1000") int count) {
        long ms = studentService.insertOneByOne(count);
        return "Inserted " + count + " students (one-by-one) in " + ms + " ms";
    }

    @GetMapping("/batch")
    public String insertBatch(@RequestParam(defaultValue = "1000") int count) {
        long ms = studentService.insertBatch(count);
        return "Inserted " + count + " students (batch) in " + ms + " ms";
    }

    @PutMapping("/{id}/age")
    public String updateStudentAge(@PathVariable long id, @RequestParam int age) {
        int updated = studentService.updateAge(id, age);
        return (updated == 1)
                ? "Updated student " + id + " age to " + age
                : "Student " + id + " not found";
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable long id) {
        int deleted = studentService.deleteById(id);
        return (deleted == 1)
                ? "Deleted student " + id
                : "Student " + id + " not found";
    }



    //    @GetMapping("/batch")
//    public String insertBatch(@RequestParam int count) {
//
//        long start = System.currentTimeMillis();
//
//        studentService.batchInsertStudents(count);
//
//        long end = System.currentTimeMillis();
//
//        return "Inserted " + count + " students in " + (end - start) + " ms";
//    }

}