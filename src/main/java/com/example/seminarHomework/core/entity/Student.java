package com.example.seminarHomework.core.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {
    @Id
    private Long id;

    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<Mark> marks = new ArrayList<>();

    private String sname;
    @Column(name = "class")
    private String classGroup;
    private Boolean boy;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public List<Mark> getMarks() {
        return marks;
    }

    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getClassGroup() {
        return classGroup;
    }

    public void setClassGroup(String classGroup) {
        this.classGroup = classGroup;
    }

    public Boolean getBoy() {
        return boy;
    }

    public void setBoy(Boolean boy) {
        this.boy = boy;
    }

    public Student(Long id, String sname, String classGroup, Boolean boy) {
        this.id = id;
        this.sname = sname;
        this.classGroup = classGroup;
        this.boy = boy;
    }

    public Student() {}
}
