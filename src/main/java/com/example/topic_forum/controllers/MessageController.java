package com.example.topic_forum.controllers;

import com.example.topic_forum.models.Message;
import com.example.topic_forum.models.Topic;
import com.example.topic_forum.services.MessageService;
import com.example.topic_forum.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private TopicService topicService;

    @GetMapping("/topic/{topicId}/addMessage")
    public String addMessagePage(@PathVariable Long topicId, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return "login";
        }
        return "addMessage";
    }


    @PostMapping("/topic/{topicId}/addMessage")
    public String addMessageToTopic(@PathVariable Long topicId, @RequestParam String messageText, @AuthenticationPrincipal UserDetails userDetails) {

        messageService.addMessageToTopic(topicId, messageText, userDetails.getUsername());
        return "redirect:/topic/{topicId}";
    }



    @GetMapping("/topic/{topicId}/editMessage/{messageId}")
    public String messageEditPage(@PathVariable Long topicId, @PathVariable Long messageId,   Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Message message = messageService.getMessageById(messageId);
            if (!message.getAuthorName().equals(userDetails.getUsername()))
                return "redirect:/topic/{topicId}";
        model.addAttribute("message", message);
        return "editMessage";
    }

    @PostMapping("/topic/{topicId}/editMessage/{messageId}")
    public String messageEdit(@PathVariable Long topicId,
                              @PathVariable Long messageId,
                              @RequestParam String messageText) {
        messageService.editMessage(messageId, messageText);
            return "redirect:/topic/{topicId}";
    }

    @PostMapping("/topic/{topicId}/deleteMessage/{messageId}")
    public String messageDelete(@PathVariable Long topicId,
                                @PathVariable Long messageId) {
        messageService.deleteMessageById(messageId);
            return "redirect:/topic/" + topicId;
    }
}
