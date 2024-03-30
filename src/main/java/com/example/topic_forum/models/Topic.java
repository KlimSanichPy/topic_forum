package com.example.topic_forum.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL)
    private List<Message> messages;

    public Topic() {}

    public Topic(String title, Message firstMessage) {
        this.title = title;
        this.messages = List.of(firstMessage);
        firstMessage.setTopic(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}