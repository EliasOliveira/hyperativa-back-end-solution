package com.example.hyperativa_back_end.services;

import com.example.hyperativa_back_end.dtos.CardRequest;
import com.example.hyperativa_back_end.dtos.CardResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class CardService {

    public CardResponse saveCard(@Valid CardRequest request) {
        log.info("Saving card: {}", request);
//      TODO finish
        return new CardResponse();
    }

    public void saveCardsFromFile(MultipartFile file) {
        //      TODO finish
    }

    public CardResponse findCard(String cardNumber) {
        //      TODO finish
        return new CardResponse();
    }
}
