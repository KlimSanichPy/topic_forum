package com.example.topic_forum.config;

import com.example.topic_forum.repositoies.UserRepository;
import com.example.topic_forum.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig{

    @Autowired
    private UserRepository userRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .authorizeHttpRequests((requests) -> requests
                            .requestMatchers("/", "/register", "/h2-console").permitAll()
                            .requestMatchers("/topic/**").authenticated())
                    .formLogin((form) -> form
                            .loginPage("/login")
                            .permitAll()
                            .defaultSuccessUrl("/topic/page_1")

                    )
                    .logout((logout) -> logout.permitAll());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserService();
    }

}