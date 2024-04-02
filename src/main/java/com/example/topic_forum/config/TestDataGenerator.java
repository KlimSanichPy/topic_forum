package com.example.topic_forum.config;

import com.example.topic_forum.models.Role;
import com.example.topic_forum.models.Topic;
import com.example.topic_forum.models.UserEntity;
import com.example.topic_forum.services.CustomUserDetailsService;
import com.example.topic_forum.services.MessageService;
import com.example.topic_forum.services.TopicService;
import com.example.topic_forum.services.UserService;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

import java.util.Set;


@Component
public class TestDataGenerator {

    @Autowired
    private TopicService topicService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private UserService userService;


    @PostConstruct
    public void generateTestData() {
        Faker faker = new Faker();

        UserEntity user1 = new UserEntity();
        user1.setUsername("sa");
        user1.setPassword("sa");
        userService.registerAdmin(user1);

        UserEntity user2 = new UserEntity();
        user2.setUsername("user2");
        user2.setPassword("password2");
        userService.registerUser(user2);

        UserEntity user3 = new UserEntity();
        user3.setUsername("user3");
        user3.setPassword("password3");
        userService.registerUser(user3);

        UserDetails userDetails1 = customUserDetailsService.loadUserByUsername(user1.getUsername());
        UserDetails userDetails2 = customUserDetailsService.loadUserByUsername(user2.getUsername());
        UserDetails userDetails3 = customUserDetailsService.loadUserByUsername(user3.getUsername());

        for (int i = 0; i < 20; i++) {
            String title = faker.lorem().sentence();

            Topic topic = topicService.createNewTopic(title);

            for (int j = 0; j < 5; j++) {
                String additionalMessageText = faker.lorem().sentence();
                if (i % 3 == 0) {
                    messageService.addMessageToTopic(topic.getId(), additionalMessageText, userDetails1.getUsername());
                } else if (i % 3 == 1) {
                    messageService.addMessageToTopic(topic.getId(), additionalMessageText, userDetails2.getUsername());
                } else {
                    messageService.addMessageToTopic(topic.getId(), additionalMessageText, userDetails3.getUsername());
                }
            }
        }
    }
}