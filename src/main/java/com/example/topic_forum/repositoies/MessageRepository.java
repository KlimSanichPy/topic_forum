package com.example.topic_forum.repositoies;

import com.example.topic_forum.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
