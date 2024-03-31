package com.example.topic_forum.controllers;

import com.example.topic_forum.models.Message;
import com.example.topic_forum.models.Topic;
import com.example.topic_forum.models.UserEntity;
import com.example.topic_forum.services.MessageService;
import com.example.topic_forum.services.TopicService;
import com.example.topic_forum.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TopicController {
    @Autowired
    private UserService userService;

    @Autowired
    private TopicService topicService;
    @Autowired
    private MessageService messageService;



    @GetMapping("/topic/page_{pageNumber}")
    public String showTopicsByPage(@PathVariable int pageNumber, Model model) {
        int pageSize = 6;

        List<Topic> topics = topicService.getTopicsByPage(pageNumber, pageSize);
        model.addAttribute("topics", topics);

        return "topics-list";
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/topic/add")
    public String showCreateTopicForm(Model model) {
        return "topic-add";
    }


    @PostMapping("/topic/add")
    public String createTopic(@RequestParam String title, @RequestParam String messageText, @AuthenticationPrincipal UserDetails userDetails) {

        Topic topic = topicService.createNewTopic(title);

        messageService.saveMessage(messageText, userDetails, topic);

        return "redirect:/topic/" + topic.getId();
    }


    @GetMapping("/topic/{topicId}")
    public String showTopic(@PathVariable Long topicId,  Model model) {
        Topic topic = topicService.getTopicById(topicId);
        if (topic == null) {
            return "redirect:/";
        }
        model.addAttribute("topic", topic);
        model.addAttribute("messages", topic.getMessages());
        return "topic";
    }

    @GetMapping("/topic/{topicId}/message-edit")
    public String messageEdit(@PathVariable Long topicId,  Model model) {
        Topic topic = topicService.getTopicById(topicId);
        if (topic == null) {
            return "redirect:/";
        }
        model.addAttribute("topic", topic);
        model.addAttribute("messages", topic.getMessages()); // Добавление списка сообщений топика в модель
        return "topic";
    }

}
