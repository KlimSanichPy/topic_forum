package com.example.topic_forum.services;
import com.example.topic_forum.models.Topic;
import com.example.topic_forum.models.Message;
import com.example.topic_forum.repositoies.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class TopicService {

    @Autowired
    private final TopicRepository topicRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public List<Topic> getTopicsByPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return topicRepository.findAll(pageable).getContent();
    }

    public Topic createNewTopic(String title, Message firstMessage) {
        Topic topic = new Topic(title, firstMessage);

        return topicRepository.save(topic);
    }

    public Topic getTopicById(Long id) {
        Optional<Topic> topicOptional = topicRepository.findById(id);
        return topicOptional.orElse(null);
    }

    public void addMessageToTopic(Long topicId, Message message) {
        Topic topic = topicRepository.findById(topicId).orElse(null);
        if (topic != null) {
            topic.addMessage(message);
            topicRepository.save(topic);
        } else {
            throw new RuntimeException("Topic not found with ID: " + topicId);
        }
    }
}
