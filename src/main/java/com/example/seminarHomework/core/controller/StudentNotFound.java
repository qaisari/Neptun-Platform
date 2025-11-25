package com.example.seminarHomework.core.controller;

public class StudentNotFound extends RuntimeException {
    public StudentNotFound(int id) {
        super("Student with ID " + id + " not found");
    }
}

