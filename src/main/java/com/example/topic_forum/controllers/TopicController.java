package com.example.topic_forum.controllers;

import com.example.topic_forum.models.Topic;
import com.example.topic_forum.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("")
public class TopicController {
    @Autowired
    private UserService userService;

    @Autowired
    private TopicService topicService;
    @Autowired
    private MessageService messageService;


    @GetMapping("/")
    public String startPage() {
        return "redirect:/topic/page_1";
    }


    @GetMapping("/topic/page_{pageNumber}")
    public String showTopicsByPage(@PathVariable int pageNumber, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            int pageSize = 6;

            List<Topic> topics = topicService.getTopicsByPage(pageNumber, pageSize);
            model.addAttribute("topics", topics);

            if (userDetails == null)
                return "topics-list-nauth";
            else
                return "topics-list-auth";
        }
        catch (TopicServiceException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }


    @GetMapping("/topic/add")
    public String showCreateTopicForm(Model model,@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null)
            return "login";
        return "topic-add";
    }


    @PostMapping("/topic/add")
    public String createTopic(@RequestParam String title,
                              @RequestParam String messageText,
                              @AuthenticationPrincipal UserDetails userDetails,
                              Model model) {
        try {
            Topic topic = topicService.createNewTopic(title);

            messageService.saveMessage(messageText, userDetails, topic);

            return "redirect:/topic/" + topic.getId();
        }
        catch (MessageServiceException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }


    @GetMapping("/topic/{topicId}")
    public String showTopic(@PathVariable Long topicId,  Model model) {
        try {
            Topic topic = topicService.getTopicById(topicId);
            if (topic == null) {
                return "redirect:/";
            }
            model.addAttribute("topic", topic);
            model.addAttribute("messages", topic.getMessages());
            return "topic";
        }
        catch (MessageServiceException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }
}
