package com.example.topic_forum.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean isFirstMessage;
    private String authorName;
    @Column(length = 1024)
    private String text;
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    public Message() {
        this.createdAt = new Date();
    }

    public Message(String text, String authorName) {
        this.authorName = authorName;
        this.text = text;
        this.createdAt = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public boolean isFirstMessage() {
        return isFirstMessage;
    }

    public void setFirstMessage(boolean firstMessage) {
        isFirstMessage = firstMessage;
    }
}
