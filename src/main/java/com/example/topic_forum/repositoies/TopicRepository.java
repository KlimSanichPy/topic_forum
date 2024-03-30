package com.example.topic_forum.repositoies;

import com.example.topic_forum.models.Topic;

import org.springframework.data.jpa.repository.JpaRepository;


public interface TopicRepository extends JpaRepository<Topic, Long> {
}
