package com.example.topic_forum.config;

import com.example.topic_forum.models.Message;
import com.example.topic_forum.models.Topic;
import com.example.topic_forum.services.MessageService;
import com.example.topic_forum.services.TopicService;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class TestDataGenerator {

    private final TopicService topicService;
    private final MessageService messageService;

    @Autowired
    public TestDataGenerator(TopicService topicService, MessageService messageService) {
        this.topicService = topicService;
        this.messageService = messageService;
    }

    @PostConstruct
    public void generateTestData() {
        Faker faker = new Faker();


        for (int i = 0; i < 20; i++) {
            String title = faker.lorem().sentence();

            String messageText = faker.lorem().sentence();
            Message firstMessage = new Message();
            firstMessage.setAuthorName("Anonymous");
            firstMessage.setText(messageText);

            Topic topic = topicService.createNewTopic(title, firstMessage);

            for (int j = 0; j < 5; j++) {
                String additionalMessageText = faker.lorem().sentence();
                Message additionalMessage = new Message();
                additionalMessage.setAuthorName("Anonymous");
                additionalMessage.setText(additionalMessageText);

                topicService.addMessageToTopic(topic.getId(), additionalMessage);
            }
        }
    }
}