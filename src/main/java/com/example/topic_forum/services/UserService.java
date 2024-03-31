package com.example.topic_forum.services;

import com.example.topic_forum.config.CustomUserDetails;
import com.example.topic_forum.models.Role;
import com.example.topic_forum.models.UserEntity;
import com.example.topic_forum.repositoies.UserRepository;
import java.util.Optional;
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
        if (userRepository.findByUsername(userEntity.getUsername()) != null) return false;
        userEntity.setActive(true);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        UserEntity u = userRepository.save(userEntity);

        Optional<UserEntity> fetchedUserOptional = userRepository.findById(u.getId());

        // Проверяем, найден ли сохраненный пользователь
        if (fetchedUserOptional.isPresent()) {
            UserEntity fetchedUser = fetchedUserOptional.get();
            System.out.println("Пользователь успешно сохранен в БД:");
            System.out.println("ID: " + fetchedUser.getId());
            System.out.println("PSWD: " + fetchedUser.getPassword());
            System.out.println("Имя пользователя: " + fetchedUser.getUsername());
            // Выводим другие свойства пользователя по необходимости
        } else {
            System.out.println("Ошибка: сохраненный пользователь не найден в БД");
        }

        return true;
    }

}