package com.example.seminarHomework.core.controller;

import com.example.seminarHomework.core.entity.Student;
import com.example.seminarHomework.core.repository.StudentRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/crud")
public class StudentCrudController {
    @Autowired private StudentRepo studentRepo;

    // READ - Display all students
    @GetMapping
    public String listStudents(Model model, HttpServletRequest request) {
        model.addAttribute("uri", request.getRequestURI());
        model.addAttribute("students", studentRepo.findAll());
        return "core/crud/studentsList";
    }

    // CREATE - Show add form
    @GetMapping("/add")
    public String showAddForm(Model model, HttpServletRequest request) {
        model.addAttribute("uri", request.getRequestURI());
        model.addAttribute("student", new Student());
        return "core/crud/studentForm";
    }

    // CREATE - Save new student
    @PostMapping("/save")
    public String saveStudent(@ModelAttribute Student student, RedirectAttributes redAttr) {
        try {
            studentRepo.save(student);
            redAttr.addFlashAttribute("message", "Student " + student.getSname() + " added successfully!");
            return "redirect:/crud/students";
        } catch (Exception e) {
            redAttr.addFlashAttribute("error", "Error adding student: " + e.getMessage());
            return "redirect:/crud/students/add";
        }
    }

    // UPDATE - Show edit form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable(name = "id") int id, Model model, HttpServletRequest request) {
        Student student = studentRepo.findById(id).orElse(null);
        if (student == null) {
            return "redirect:/crud/students";
        }
        model.addAttribute("uri", request.getRequestURI());
        model.addAttribute("student", student);
        return "core/crud/studentForm";
    }

    // UPDATE - Update existing student
    @PostMapping("/update")
    public String updateStudent(@ModelAttribute Student student, RedirectAttributes redAttr) {
        try {
            studentRepo.save(student);
            redAttr.addFlashAttribute("message", "Student " + student.getSname() + " updated successfully!");
            return "redirect:/crud/students";
        } catch (Exception e) {
            redAttr.addFlashAttribute("error", "Error updating student: " + e.getMessage());
            return "redirect:/crud/students/edit/" + student.getId();
        }
    }

    // DELETE - Delete student
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable(name = "id") int id, RedirectAttributes redAttr) {
        try {
            Student student = studentRepo.findById(id).orElse(null);
            if (student != null) {
                studentRepo.deleteById(id);
                redAttr.addFlashAttribute("message", "Student " + student.getSname() + " deleted successfully!");
            }
        } catch (Exception e) {
            redAttr.addFlashAttribute("error", "Error deleting student: " + e.getMessage());
        }
        return "redirect:/crud/students";
    }
}

