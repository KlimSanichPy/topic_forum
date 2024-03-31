package com.example.topic_forum.services;

import com.example.topic_forum.models.Message;
import com.example.topic_forum.repositoies.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    public Message editMessage(Long messageId, String newText) {
        Message existingMessage = messageRepository.findById(messageId).orElse(null);
        if (existingMessage != null) {
            existingMessage.setText(newText);
            return messageRepository.save(existingMessage);
        }
        return null; // Если сообщение не найдено, возвращаем null или можно выбросить исключение
    }

    public void deleteMessageById(Long messageId) { messageRepository.deleteById(messageId);}

}
