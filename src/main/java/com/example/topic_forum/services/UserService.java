package com.example.topic_forum.services;

import com.example.topic_forum.config.CustomUserDetails;
import com.example.topic_forum.models.Role;
import com.example.topic_forum.models.UserEntity;
import com.example.topic_forum.repositoies.UserRepository;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean registerUser(UserEntity userEntity) {
        userEntity.setRoles(Set.of(Role.USER));
        if (userRepository.findByUsername(userEntity.getUsername()) != null) return false;
        userEntity.setActive(true);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);

        return true;
    }

}