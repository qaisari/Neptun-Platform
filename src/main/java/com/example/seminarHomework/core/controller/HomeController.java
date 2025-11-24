package com.example.seminarHomework.core.controller;

import com.example.seminarHomework.core.entity.Mark;
import com.example.seminarHomework.core.entity.Student;
import com.example.seminarHomework.core.entity.Subject;
import com.example.seminarHomework.core.repository.MarkRepo;
import com.example.seminarHomework.core.repository.StudentRepo;
import com.example.seminarHomework.core.repository.SubjectRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired private StudentRepo studRepo;
    @Autowired private SubjectRepo subjRepo;
    @Autowired private MarkRepo markRepo;

    @GetMapping("/")
    public String home() {
        return "core/index";
    }
    @GetMapping("/home")
    public String user(Model model, HttpServletRequest request) {
        model.addAttribute("uri",  request.getRequestURI());
        return "core/users/user";
    }

    @GetMapping("/datamenu")
    public String datamenu(Model model, HttpServletRequest request) {
        model.addAttribute("uri",  request.getRequestURI());
        return "core/datamenu";
    }

    @GetMapping("/datamenu/student")
    public String studentsData(Model model, HttpServletRequest request) {
        model.addAttribute("uri",  request.getRequestURI());
        model.addAttribute("students", studRepo.findAll());
        model.addAttribute("dataType", "student");
        return "core/datamenu";
    }

    @GetMapping("/datamenu/subject")
    public String subjectsData(Model model, HttpServletRequest request) {
        model.addAttribute("uri",  request.getRequestURI());
        model.addAttribute("subjects", subjRepo.findAll());
        model.addAttribute("dataType", "subject");
        return "core/datamenu";
    }

    @GetMapping("/datamenu/mark")
    public String marksData(Model model, HttpServletRequest request) {
        model.addAttribute("uri",  request.getRequestURI());
        model.addAttribute("marks", markRepo.findAll());
        model.addAttribute("dataType", "mark");
        return "core/datamenu";
    }

    // ...existing code...
    String FormatedStudentData() {
        String str="";
        for (Student student : studRepo.findAll()) {
            str += student.getId() + "; " + student.getSname() + "; " + student.getClassGroup() + "; " +
                    student.getBoy();
            str += "<br>";
        }
        return str;
    }

    String FormatedSubjectData() {
        String str="";
        for (Subject subject : subjRepo.findAll()) {
            str += subject.getId() + "; " + subject.getSname() + "; " + subject.getCategory();
            str += "<br>";
        }
        return str;
    }

    String FormatedMarkData() {
        String str="";
        for (Mark mark : markRepo.findAll()) {
            str += mark.getStudent().getId() + "; " + mark.getMdate() + "; " + mark.getMark() + "; " +
                    mark.getType() + "; " + mark.getSubject().getId();
            str += "<br>";
        }
        return str;
    }
}
