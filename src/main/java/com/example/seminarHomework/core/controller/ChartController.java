package com.example.seminarHomework.core.controller;

import com.example.seminarHomework.core.repository.MarkRepo;
import com.example.seminarHomework.core.repository.StudentRepo;
import com.example.seminarHomework.core.repository.SubjectRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ChartController {
    @Autowired private MarkRepo markRepo;
    @Autowired private StudentRepo studentRepo;
    @Autowired private SubjectRepo subjectRepo;

    @GetMapping("/charts")
    public String chartsPage(Model model, HttpServletRequest request) {
        model.addAttribute("uri", request.getRequestURI());
        return "core/charts/charts";
    }

    @GetMapping("api/charts/marks-by-subject")
    @ResponseBody
    public Map<String, Object> getMarksBySubject() {
        List<com.example.seminarHomework.core.entity.Mark> marks = new ArrayList<>();
        markRepo.findAll().forEach(marks::add);

        // Group marks by subject
        Map<String, List<Integer>> marksBySubject = marks.stream()
                .collect(Collectors.groupingBy(
                        m -> "Subject " + m.getSubject().getId(),
                        Collectors.mapping(m -> m.getMark(), Collectors.toList())
                ));

        // Calculate average marks per subject
        Map<String, Double> avgMarks = new LinkedHashMap<>();
        marksBySubject.forEach((subject, markList) -> {
            double avg = markList.stream().mapToInt(Integer::intValue).average().orElse(0);
            avgMarks.put(subject, Math.round(avg * 100.0) / 100.0);
        });

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("labels", avgMarks.keySet().stream().collect(Collectors.toList()));
        response.put("values", avgMarks.values().stream().collect(Collectors.toList()));
        return response;
    }

    @GetMapping("api/charts/marks-by-student")
    @ResponseBody
    public Map<String, Object> getMarksByStudent() {
        List<com.example.seminarHomework.core.entity.Mark> marks = new ArrayList<>();
        markRepo.findAll().forEach(marks::add);

        // Group marks by student
        Map<String, List<Integer>> marksByStudent = marks.stream()
                .collect(Collectors.groupingBy(
                        m -> m.getStudent().getSname(),
                        Collectors.mapping(m -> m.getMark(), Collectors.toList())
                ));

        // Calculate average marks per student
        Map<String, Double> avgMarks = new LinkedHashMap<>();
        marksByStudent.forEach((student, markList) -> {
            double avg = markList.stream().mapToInt(Integer::intValue).average().orElse(0);
            avgMarks.put(student, Math.round(avg * 100.0) / 100.0);
        });

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("labels", avgMarks.keySet().stream().collect(Collectors.toList()));
        response.put("values", avgMarks.values().stream().collect(Collectors.toList()));
        return response;
    }

    @GetMapping("api/charts/mark-distribution")
    @ResponseBody
    public Map<String, Object> getMarkDistribution() {
        List<com.example.seminarHomework.core.entity.Mark> marks = new ArrayList<>();
        markRepo.findAll().forEach(marks::add);

        // Count marks in ranges: 1-2, 3-4, 5
        long range1_2 = marks.stream().filter(m -> m.getMark() >= 1 && m.getMark() <= 2).count();
        long range3_4 = marks.stream().filter(m -> m.getMark() >= 3 && m.getMark() <= 4).count();
        long range5 = marks.stream().filter(m -> m.getMark() == 5).count();

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("labels", Arrays.asList("1-2 (Poor)", "3-4 (Good)", "5 (Excellent)"));
        response.put("values", Arrays.asList(range1_2, range3_4, range5));
        return response;
    }

    @GetMapping("api/charts/student-count")
    @ResponseBody
    public Map<String, Object> getStudentCount() {
        List<com.example.seminarHomework.core.entity.Student> students = new ArrayList<>();
        studentRepo.findAll().forEach(students::add);

        long boys = students.stream().filter(s -> s.getBoy() != null && s.getBoy()).count();
        long girls = students.stream().filter(s -> s.getBoy() == null || !s.getBoy()).count();

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("labels", Arrays.asList("Boys", "Girls"));
        response.put("values", Arrays.asList(boys, girls));
        return response;
    }

    @GetMapping("api/charts/subject-count")
    @ResponseBody
    public Map<String, Object> getSubjectCount() {
        List<com.example.seminarHomework.core.entity.Subject> subjects = new ArrayList<>();
        subjectRepo.findAll().forEach(subjects::add);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("labels", subjects.stream().map(s -> s.getSname()).collect(Collectors.toList()));
        response.put("values", subjects.stream().map(s -> s.getMarks().size()).collect(Collectors.toList()));
        return response;
    }
}

