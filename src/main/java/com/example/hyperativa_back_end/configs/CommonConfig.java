package com.example.hyperativa_back_end.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {

    @Bean
    public ObjectMapper objectMapper() {
//        Customize objectMapper here
        return new ObjectMapper();
    }

}
