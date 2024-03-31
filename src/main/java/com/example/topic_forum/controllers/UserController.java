package com.example.topic_forum.controllers;

import com.example.topic_forum.models.UserEntity;
import com.example.topic_forum.repositoies.UserRepository;
import com.example.topic_forum.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String startPage(Model model) {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String authorizationPage(Model model) {
        return "login";
    }


    @GetMapping("/registr")
    public String showRegistrationForm(Model model) {
        return "registration";
    }

    @PostMapping("/registr")
    public String registerUser(UserEntity userEntity, Model model) {

        userService.registerUser(userEntity);

        return "redirect:/login";
    }
}
