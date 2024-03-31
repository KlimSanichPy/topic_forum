package com.example.topic_forum.controllers;

import com.example.topic_forum.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/topics/{topicId}/addMessage")
    public String addMessageToTopic(@PathVariable Long topicId, @RequestParam String messageText, @AuthenticationPrincipal UserDetails userDetails) {
        messageService.addMessageToTopic(topicId, messageText, userDetails);
        return "redirect:/topics/{topicId}";
    }
}
