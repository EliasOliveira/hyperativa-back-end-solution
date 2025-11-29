package com.example.hyperativa_back_end.services;

import com.example.hyperativa_back_end.dtos.CardRequest;
import com.example.hyperativa_back_end.dtos.CardResponse;
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
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;

    public CardResponse saveCard(@Valid CardRequest request) {
        log.info("Saving card: {}", request);

        // Hash the card number securely
        String salt = UUID.randomUUID().toString();
        String panHash = hashCardNumber(request.getCardNumber(), salt);

        // Avoid duplicates
        if (cardRepository.existsByPanHash(panHash)) {
            log.info("Card already exists");
            throw new CardAlreadyExistsException("Card already exists");
        }

        Card card = new Card();
        card.setPanHash(panHash);
        card.setSalt(salt);
        card.setLast4(getLast4(request.getCardNumber()));
        card.setToken(UUID.randomUUID().toString());

        cardRepository.save(card);

        return new CardResponse(card.getId(), card.getLast4());
    }

    public void saveCardsFromFile(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    CardRequest request = new CardRequest();
                    request.setCardNumber(line.trim());
                    saveCard(request);
                }
            }
        } catch (Exception e) {
            log.error("Error processing file", e);
        }
    }

    public CardResponse findCard(String cardNumber) {
        log.info("Searching card: {}", cardNumber);

        for (Card card : cardRepository.findAll()) {
            return new CardResponse(card.getId(), card.getLast4());
        }

        throw new CardNotFoundException("Card not found");
    }

    private String getLast4(String cardNumber) {
        return cardNumber.length() > 4 ? cardNumber.substring(cardNumber.length() - 4) : cardNumber;
    }
}