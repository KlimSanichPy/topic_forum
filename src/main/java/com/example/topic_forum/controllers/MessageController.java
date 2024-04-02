package com.example.topic_forum.controllers;

import com.example.topic_forum.models.Message;
import com.example.topic_forum.models.Role;
import com.example.topic_forum.services.MessageService;
import com.example.topic_forum.services.MessageServiceException;
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
    public String addMessagePage(@PathVariable Long topicId,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return "login";
        }
        return "addMessage";
    }


    @PostMapping("/topic/{topicId}/addMessage")
    public String addMessageToTopic(@PathVariable Long topicId,
                                    @RequestParam String messageText,
                                    @AuthenticationPrincipal UserDetails userDetails,
                                    Model model) {
        try {
            messageService.addMessageToTopic(topicId, messageText, userDetails.getUsername());
            return "redirect:/topic/{topicId}";
        }
        catch (MessageServiceException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }



    @GetMapping("/topic/{topicId}/editMessage/{messageId}")
    public String messageEditPage(@PathVariable Long topicId,
                                  @PathVariable Long messageId,
                                  Model model,
                                  @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Message message = messageService.getMessageById(messageId);
            if (!message.getAuthorName().equals(userDetails.getUsername())) {
                if (userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
                    return "redirect:/admin/topic/{topicId}/deleteMessage/{messageId}";
                }
                return "redirect:/topic/{topicId}";
            }
            model.addAttribute("message", message);
            return "editMessage";
        }
        catch (MessageServiceException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/topic/{topicId}/editMessage/{messageId}")
    public String messageEdit(@PathVariable Long topicId,
                              @PathVariable Long messageId,
                              @RequestParam String messageText,
                              Model model) {
        try {
            messageService.editMessage(messageId, messageText);
            return "redirect:/topic/{topicId}";
        }
        catch (MessageServiceException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/topic/{topicId}/deleteMessage/{messageId}")
    public String messageDelete(@PathVariable Long topicId,
                                @PathVariable Long messageId,
                                Model model) {
        try {
            messageService.deleteMessageById(messageId);
            return "redirect:/topic/" + topicId;
        }
        catch (MessageServiceException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }
}
