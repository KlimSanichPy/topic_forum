package com.example.topic_forum.controllers;

import com.example.topic_forum.models.Message;
import com.example.topic_forum.models.Topic;
import com.example.topic_forum.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping()
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private TopicService topicService;
    @Autowired
    private MessageService messageService;

    @GetMapping("/admin/deleteTopic/{topicId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteTopicPage(@PathVariable Long topicId, Model model) {
        Topic topic = topicService.getTopicById(topicId);
        if (topic != null) {
            model.addAttribute("topicId", topicId);
            model.addAttribute("topicTitle", topic.getTitle());
            return "adminTopicDelete";
        } else {
            return "error";
        }
    }


    @PostMapping("/admin/deleteTopic/{topicId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteTopic(@PathVariable Long topicId) {
        try {
            topicService.deleteById(topicId);
            return "redirect:/topic/page_1";
        }
        catch (TopicServiceException e) {
            return "error";
        }
    }


    @GetMapping("/admin/topic/{topicId}/deleteMessage/{messageId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteMessagePage(@PathVariable Long topicId, @PathVariable Long messageId, Model model) {
        Message message = messageService.getMessageById(messageId);
        if (message != null) {
            model.addAttribute("topicId", topicId);
            model.addAttribute("messageId", messageId);
            model.addAttribute("topicName", message.getTopic().getTitle());
            model.addAttribute("authorName", message.getAuthorName());
            model.addAttribute("messageText", message.getText());
            return "adminMessageDelete";
        } else {
            return "error";
        }
    }

    @PostMapping("/admin/topic/{topicId}/deleteMessage/{messageId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteMessage(@PathVariable Long messageId, @PathVariable Long topicId) {
        try {
            messageService.deleteMessageById(messageId);
            return "redirect:/topic/{topicId}";
        }
        catch (TopicServiceException e) {
            return "error";
        }
    }
}