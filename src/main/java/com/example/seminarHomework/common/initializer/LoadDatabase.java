package com.example.seminarHomework.common.initializer;

import com.example.seminarHomework.core.entity.Mark;
import com.example.seminarHomework.core.entity.Student;
import com.example.seminarHomework.core.entity.Subject;
import com.example.seminarHomework.core.repository.MarkRepo;
import com.example.seminarHomework.core.repository.StudentRepo;
import com.example.seminarHomework.core.repository.SubjectRepo;
import com.example.seminarHomework.core.repository.UserRepo;
import com.example.seminarHomework.core.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.InputStream;
import java.sql.Date;
import java.util.Scanner;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Value("${app.admin.password}")
    private String adminPassword;

    @Value("${app.user1.password}")
    private String user1Password;

    @Bean
    CommandLineRunner initDatabase(UserRepo userRepo,
                                   StudentRepo studRepo,
                                   SubjectRepo subjRepo,
                                   MarkRepo markRepo) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return args -> {
            //User
            if(userRepo.count() == 0) {
                userRepo.save(new User("Admin","admin@gmail.com",passwordEncoder.encode(adminPassword),"ROLE_ADMIN"));
                userRepo.save(new User("User1","user@gmail.com",passwordEncoder.encode(user1Password),"ROLE_USER"));
            }

            //Student
            if(studRepo.count() == 0) {
                InputStream student = getClass().getResourceAsStream("/static/data/students.txt");
                if(student != null) {
                    try (Scanner sc = new Scanner(student)) {
                        while (sc.hasNextLine()) {
                            String line = sc.nextLine().trim();
                            if (line.isEmpty() || line.startsWith("id")) continue;
                            String[] parts = line.split("\t");
                            try {
                                // Parse boy field: -1 = true (boy), 0 = false (girl)
                                String Gender = parts[3];
                                boolean isBoy;
                                if (Gender.equals("-1")) {
                                    isBoy = true;
                                } else {
                                    isBoy = false;
                                }
                                studRepo.save(new Student(Long.parseLong(parts[0]), parts[1], parts[2], isBoy));
                            }catch (Exception e) {
                                log.warn("Failed to parse student line {}: {}", line, e.getMessage());
                            }
                        }
                    }
                } else {
                    log.warn("students.txt not found on classpath at /static/data/students.txt");
                }
            }

            //Subject
            if(subjRepo.count() == 0) {
                InputStream subject = getClass().getResourceAsStream("/static/data/subjects.txt");
                if(subject != null) {
                    try (Scanner sc = new Scanner(subject)) {
                        while (sc.hasNextLine()) {
                            String line = sc.nextLine().trim();
                            if (line.isEmpty() || line.startsWith("id")) continue;
                            String[] parts = line.split("\t");
                            try {
                                subjRepo.save(new Subject(Integer.parseInt(parts[0]), parts[1], parts[2]));
                            }catch (Exception e) {
                                log.warn("Failed to parse subject line {}: {}", line, e.getMessage());
                            }
                        }
                    }
                } else {
                    log.warn("subjects.txt not found on classpath at /static/data/subjects.txt");
                }
            }

            //Mark
            if(markRepo.count() == 0) {
                InputStream mark = getClass().getResourceAsStream("/static/data/marks.txt");
                if(mark != null) {
                    try (Scanner sc = new Scanner(mark)) {
                        while (sc.hasNextLine()) {
                            String line = sc.nextLine().trim();
                            if (line.isEmpty() || line.startsWith("studentid")) continue;
                            String[] parts = line.split("\t");
                            try {
                                Integer studentid = Integer.parseInt(parts[0]);
                                Student student = studRepo.findById(studentid).orElse(null);
                                if (student == null) {
                                    log.warn("Skipping mark row: student id {} not found", studentid);
                                    continue;
                                }

                                Integer subjectId = Integer.parseInt(parts[4]);
                                Subject subject = subjRepo.findById(subjectId).orElse(null);
                                if (subject == null) {
                                    log.warn("Skipping mark row: subject id {} not found", subjectId);
                                    continue;
                                }

                                // Parse date format: yyyy.MM.dd -> yyyy-MM-dd for SQL
                                String dateStr = parts[1].replace(".", "-");
                                Date markDate = Date.valueOf(dateStr);
                                Integer markValue = Integer.parseInt(parts[2]);
                                String markType = parts[3];

                                markRepo.save(new Mark(student, markDate, markValue, markType, subject));
                                log.debug("Successfully loaded mark for student {} on subject {}", studentid, subjectId);
                            }catch (Exception e) {
                                log.warn("Failed to parse mark line {}: {}", line, e.getMessage());
                            }
                        }
                    }
                } else {
                    log.warn("marks.txt not found on classpath at /static/data/marks.txt");
                }
            }
        };
    }
}

