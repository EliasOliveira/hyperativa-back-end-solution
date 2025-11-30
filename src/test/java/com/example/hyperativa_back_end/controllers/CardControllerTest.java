package com.example.hyperativa_back_end.controllers;

import com.example.hyperativa_back_end.dtos.CardRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class CardControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Value("classpath:cards.json")
    private Resource cardsJson;

    @Value("classpath:cards_base64.txt")
    private Resource cardsBase64;

    @Test
    void testCheckCardEndpoint() throws Exception {
        var request = getCardsMock().get(1);

        mockMvc.perform(post("/api/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardNumber").value(request.getCardNumber()));

        mockMvc.perform(get("/api/cards/" + request.getCardNumber()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardNumber").value(request.getCardNumber()));
    }


    @Test
    void testInsertCardEndpoint() throws Exception {
        var request = getCardsMock().get(0);

        mockMvc.perform(post("/api/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardNumber").value(request.getCardNumber()));
    }


    @Test
    void testUploadCardsBase64Endpoint() throws Exception {
        var base64File = getCardsBase64Mock();

        var requestJson = "{ \"fileBase64\": \"" + base64File + "\" }";

        mockMvc.perform(post("/api/cards/upload")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Cards uploaded successfully"));
    }

    @Test
    void testCardAlreadyExists() throws Exception {
        var request = getCardsMock().get(2);

        mockMvc.perform(post("/api/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardNumber").value(request.getCardNumber()));

        mockMvc.perform(post("/api/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testCardNotFoundExists() throws Exception {
        var request = getCardsMock().get(3);

        mockMvc.perform(get("/api/cards/" + request.getCardNumber()))
                .andExpect(status().isNotFound());
    }

    private List<CardRequest> getCardsMock() throws IOException {
        return objectMapper.readValue(cardsJson.getInputStream(), new TypeReference<List<CardRequest>>() {});
    }

    private String getCardsBase64Mock() throws IOException {
        return new String(cardsBase64.getInputStream().readAllBytes(), StandardCharsets.UTF_8).trim();
    }

}