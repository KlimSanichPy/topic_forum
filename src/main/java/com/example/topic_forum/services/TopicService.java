package com.example.topic_forum.services;
import com.example.topic_forum.models.Topic;
import com.example.topic_forum.models.Message;
import com.example.topic_forum.repositoies.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
public class TopicService {

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
}
