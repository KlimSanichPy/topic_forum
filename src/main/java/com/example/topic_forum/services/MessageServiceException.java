package com.example.topic_forum.services;

public class MessageServiceException extends RuntimeException {

    public MessageServiceException(String message) {
        super(message);
    }

    public MessageServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}