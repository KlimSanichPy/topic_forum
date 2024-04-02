package com.example.topic_forum.services;
import com.example.topic_forum.models.Message;
import com.example.topic_forum.models.Topic;
import com.example.topic_forum.repositoies.MessageRepository;
import com.example.topic_forum.repositoies.TopicRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private MessageRepository messageRepository;

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<Topic> getTopicsByPage(int pageNumber, int pageSize) {
        try {
            Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
            return topicRepository.findAll(pageable).getContent();
        } catch (Exception e) {
            throw new TopicServiceException("Error occurred while fetching topics by page", e);
        }
    }

    @Transactional
    public Topic createNewTopic(String title) {
        try {
            Topic topic = new Topic(title);
            return topicRepository.save(topic);
        } catch (Exception e) {
            throw new TopicServiceException("Error occurred while creating a new topic", e);
        }
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public Topic getTopicById(Long id) {
        try {
            Optional<Topic> topicOptional = topicRepository.findById(id);
            return topicOptional.orElse(null);
        } catch (Exception e) {
            throw new TopicServiceException("Error occurred while fetching topic by ID", e);
        }
    }

    @org.springframework.transaction.annotation.Transactional
    public void deleteById(Long id) {
        try {
            Topic topic = topicRepository.findById(id).orElseThrow(() -> new TopicServiceException("Topic not found with id: " + id));
            topic.getMessages().clear();
            topicRepository.delete(topic);
        } catch (Exception e) {
            throw new TopicServiceException("Error occurred while deleting topic by ID", e);
        }
    }

}
