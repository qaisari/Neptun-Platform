package com.example.seminarHomework.core.repository;

import com.example.seminarHomework.core.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m ORDER BY m.sentAt DESC")
    List<Message> findAllOrderByNewest();
}

