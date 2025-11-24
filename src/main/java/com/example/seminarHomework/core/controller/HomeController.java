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
    @GetMapping("/")
    public String home() {
        return "core/index";
    }

    @GetMapping("/home")
    public String user(Model model, HttpServletRequest request) {
        model.addAttribute("uri",  request.getRequestURI());
        return "core/users/user";
    }
}
