package com.example.topic_forum.controllers;

import com.example.topic_forum.models.UserEntity;
import com.example.topic_forum.repositoies.UserRepository;
import com.example.topic_forum.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String startPage(Model model) {
        return "redirect:/login";
    }


    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@RequestParam String username, @RequestParam String password, Model model) {
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword(password);
        userEntity.setUsername(username);
        boolean registrationSuccess = userService.registerUser(userEntity);
        if (registrationSuccess) {
            return "redirect:/login";
        } else {
            model.addAttribute("errorMessage", "User with name " + userEntity.getUsername() + " already exists");
            return "registration";
        }
    }
}
