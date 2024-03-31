package com.example.topic_forum.services;

import com.example.topic_forum.models.Message;
import com.example.topic_forum.models.Topic;
import com.example.topic_forum.repositoies.MessageRepository;
import com.example.topic_forum.repositoies.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private TopicRepository topicRepository;


    public Message saveMessage(String messageText, UserDetails userDetails, Topic topic) {
        Message message = new Message();

        message.setText(messageText);
        message.setTopic(topic);
        message.setAuthorName(userDetails.getUsername());

        return messageRepository.save(message);
    }

    public Message editMessage(Long messageId, String newText) {
        Message existingMessage = messageRepository.findById(messageId).orElse(null);
        if (existingMessage != null) {
            existingMessage.setText(newText);
            return messageRepository.save(existingMessage);
        }
        return null;
    }

    public void addMessageToTopic(Long topicId, String messageText, UserDetails userDetails) {

        Message message = new Message();
        message.setText(messageText);
        message.setTopic(topicRepository.getReferenceById(topicId));
        message.setAuthorName(userDetails.getUsername());

        messageRepository.save(message);
    }

    public void deleteMessageById(Long messageId) { messageRepository.deleteById(messageId);}

}
