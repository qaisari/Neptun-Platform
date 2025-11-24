package com.example.seminarHomework.core.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "subjects")
public class Subject {
    @Id
    private int id;
    private String sname;
    private String category;

    @OneToMany(mappedBy = "subject")
    private List<Mark> marks;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }

    public Subject(int id, String sname, String category) {
        this.id = id;
        this.sname = sname;
        this.category = category;
    }

    public Subject() {}
}
