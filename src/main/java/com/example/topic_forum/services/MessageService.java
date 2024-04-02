package com.example.topic_forum.services;

import com.example.topic_forum.models.Message;
import com.example.topic_forum.models.Topic;
import com.example.topic_forum.repositoies.MessageRepository;
import com.example.topic_forum.repositoies.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private TopicRepository topicRepository;

    @org.springframework.transaction.annotation.Transactional
    public Message saveMessage(String messageText, UserDetails userDetails, Topic topic) {
        try {
            Message message = new Message();
            message.setText(messageText);
            message.setTopic(topic);
            message.setAuthorName(userDetails.getUsername());
            return messageRepository.save(message);
        } catch (Exception e) {
            throw new MessageServiceException("Error occurred while saving message", e);
        }
    }

    @org.springframework.transaction.annotation.Transactional
    public Message editMessage(Long messageId, String newText) {
        try {
            Message existingMessage = messageRepository.findById(messageId).orElse(null);
            if (existingMessage != null) {
                existingMessage.setText(newText);
                return messageRepository.save(existingMessage);
            }
            return null;
        } catch (Exception e) {
            throw new MessageServiceException("Error occurred while editing message", e);
        }
    }

    @org.springframework.transaction.annotation.Transactional
    public void addMessageToTopic(Long topicId, String messageText, String userName) {
        try {
            Message message = new Message();
            message.setText(messageText);
            message.setTopic(topicRepository.getReferenceById(topicId));
            message.setAuthorName(userName);
            messageRepository.save(message);
        } catch (Exception e) {
            throw new MessageServiceException("Error occurred while adding message to topic", e);
        }
    }

    @org.springframework.transaction.annotation.Transactional
    public void deleteMessageById(Long messageId) {
        try {
            Message message = getMessageById(messageId);
            Topic topic = message.getTopic();
            if (topic.getMessages().size() <= 1) {
                throw new MessageServiceException("Cannot delete the last message in the topic");
            }
            topic.getMessages().remove(message);
            messageRepository.deleteById(messageId);
        } catch (Exception e) {
            throw new MessageServiceException("Error occurred while deleting message", e);
        }
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public Message getMessageById(Long id) {
        try {
            Optional<Message> MessageOptional = messageRepository.findById(id);
            return MessageOptional.orElse(null);
        } catch (Exception e) {
            throw new MessageServiceException("Error occurred while fetching message by ID", e);
        }
    }

}
