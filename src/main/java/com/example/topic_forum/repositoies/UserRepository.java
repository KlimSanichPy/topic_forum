package com.example.topic_forum.repositoies;

import com.example.topic_forum.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
