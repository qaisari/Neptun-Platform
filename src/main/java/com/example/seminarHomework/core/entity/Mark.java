package com.example.seminarHomework.core.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "marks")
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "studentid")
    private Student student;

    private Date mdate;
    private int mark;
    private String type;

    @ManyToOne
    @JoinColumn(name = "subjectid")
    private Subject subject;

    public Long getId() { return id; }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Date getMdate() {
        return mdate;
    }

    public void setMdate(Date mdate) {
        this.mdate = mdate;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Mark(Student student, Date mdate, int mark, String type, Subject subject) {
        this.student = student;
        this.mdate = mdate;
        this.mark = mark;
        this.type = type;
        this.subject = subject;
    }

    public Mark() {}
}
