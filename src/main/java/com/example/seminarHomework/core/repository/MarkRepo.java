package com.example.seminarHomework.core.repository;

import com.example.seminarHomework.core.entity.Mark;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkRepo extends CrudRepository<Mark, Integer> {
}
