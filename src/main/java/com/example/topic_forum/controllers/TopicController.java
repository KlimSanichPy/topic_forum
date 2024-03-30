package com.example.topic_forum.controllers;
import com.example.topic_forum.models.Topic;
import com.example.topic_forum.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping
    public String startPage() {
        return "welcomePage";
    }

    @GetMapping("/topics_page_{pageNumber}")
    public String showTopicsByPage(@PathVariable int pageNumber, Model model) {
        int pageSize = 6;

        List<Topic> topics = topicService.getTopicsByPage(pageNumber, pageSize);
        model.addAttribute("topics", topics);

        return "topics";
    }




}
