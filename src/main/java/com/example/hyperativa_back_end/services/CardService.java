package com.example.hyperativa_back_end.services;

import com.example.hyperativa_back_end.dtos.CardRequest;
import com.example.hyperativa_back_end.dtos.CardResponse;
import com.example.hyperativa_back_end.dtos.beanio.CardRecord;
import com.example.hyperativa_back_end.entities.Card;
import com.example.hyperativa_back_end.exceptions.CardAlreadyExistsException;
import com.example.hyperativa_back_end.exceptions.CardNotFoundException;
import com.example.hyperativa_back_end.repositories.CardRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardService {

    private final PositionalFileService positionalFileService;
    private final CardRepository cardRepository;

    public CardResponse saveCard(@Valid CardRequest request) {
        log.info("Saving card: {}", request);

        // Avoid duplicates
        if (cardRepository.existsByNumber(request.getCardNumber())) {
            log.info("Card already exists");
            throw new CardAlreadyExistsException("Card already exists");
        }

        Card card = new Card(request.getCardNumber());
        cardRepository.save(card);

        return new CardResponse(card.getId(), card.getNumber());
    }

    public void saveCardsFromBase64(String base64Content) {
        log.info("Saving cards");
        List<CardRecord> records = positionalFileService.parseCards(base64Content);
        records.stream()
                .map(record -> new CardRequest(record.getCardNumber()))
                .forEach(this::saveCard);
    }

    public CardResponse findCard(String cardNumber) {
        log.info("Searching card: {}", cardNumber);
        return cardRepository.findByNumber(cardNumber)
                .map(card -> new CardResponse(card.getId(), card.getNumber()))
                .orElseThrow(() -> new CardNotFoundException("Card not found"));
    }

}