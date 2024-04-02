package com.example.topic_forum.services;

public class TopicServiceException extends RuntimeException {

    public TopicServiceException(String message) {
        super(message);
    }

    public TopicServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}