package com.example.seminarHomework.core.controller;

import com.example.seminarHomework.core.entity.Student;
import com.example.seminarHomework.core.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class StudentRestController {

    @Autowired
    private StudentRepo studentRepo;

    // GET all students
    @GetMapping("/students")
    public Iterable<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    // GET a specific student by ID
    @GetMapping("/students/{id}")
    public Student getStudentById(@PathVariable int id) {
        return studentRepo.findById(id)
                .orElseThrow(() -> new StudentNotFound(id));
    }

    // POST - Create a new student
    @PostMapping("/students")
    Student createStudent(@RequestBody Student student) { return studentRepo.save(student); }

    // PUT - Update an existing student
    @PutMapping("/students/{id}")
    public Student updateStudent(@PathVariable int id, @RequestBody Student student) {
        return studentRepo.findById(id)
                .map(newStudent -> {
                    newStudent.setSname(student.getSname());
                    newStudent.setClassGroup(student.getClassGroup());
                    newStudent.setBoy(student.getBoy());
                    return studentRepo.save(newStudent);
                }).orElseGet(() -> {
                    return  studentRepo.save(student);
                });
    }

    // DELETE - Delete a student
    @DeleteMapping("/students/{id}")
    public void deleteStudent(@PathVariable int id) {
        studentRepo.deleteById(id);
    }
}

