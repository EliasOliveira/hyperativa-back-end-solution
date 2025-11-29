package com.example.hyperativa_back_end.configs;

import com.example.hyperativa_back_end.entities.User;
import com.example.hyperativa_back_end.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@Slf4j
public class CommonConfig {

    @Bean
    public ObjectMapper objectMapper() {
//        Customize objectMapper here
        return new ObjectMapper();
    }

//    Database initialization

    @Bean
    CommandLineRunner initAdminUser(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

                User admin = new User();
                admin.setUsername("admin");
                admin.setPasswordHash(encoder.encode("admin123"));

                userRepository.save(admin);
                log.info("✅ Admin user created: admin/admin123");
            } else {
                log.info("ℹ️ Admin user already exists, skipping creation.");
            }
        };
    }


}
