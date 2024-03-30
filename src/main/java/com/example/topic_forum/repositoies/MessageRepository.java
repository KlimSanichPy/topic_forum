package com.example.topic_forum.repositoies;

import com.example.topic_forum.models.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {
}
