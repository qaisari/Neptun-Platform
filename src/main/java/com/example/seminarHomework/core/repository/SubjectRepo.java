package com.example.seminarHomework.core.repository;

import com.example.seminarHomework.core.entity.Subject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepo extends CrudRepository<Subject, Integer> {
}
