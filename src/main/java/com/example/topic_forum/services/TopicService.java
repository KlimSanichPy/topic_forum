package com.example.topic_forum.services;
import com.example.topic_forum.models.Topic;
import com.example.topic_forum.models.Message;
import com.example.topic_forum.repositoies.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;



    public List<Topic> getTopicsByPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        return topicRepository.findAll(pageable).getContent();
    }


    public Topic createNewTopic(String title) {

        Topic topic = new Topic(title);

        return topicRepository.save(topic);
    }

    public Topic getTopicById(Long id) {
        Optional<Topic> topicOptional = topicRepository.findById(id);
        return topicOptional.orElse(null);
    }

}
