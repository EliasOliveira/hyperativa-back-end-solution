package com.example.hyperativa_back_end.controllers;

import com.example.hyperativa_back_end.dtos.CardRequest;
import com.example.hyperativa_back_end.dtos.CardResponse;
import com.example.hyperativa_back_end.services.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/cards")
@Tag(name = "Card API", description = "Endpoints for card registration and lookup")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @Operation(
            summary = "Insert a single card",
            description = "Registers a single card number securely in the database.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Card successfully registered",
                            content = @Content(schema = @Schema(implementation = CardResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    @PostMapping
    public ResponseEntity<CardResponse> insertCard(
            @Valid @RequestBody CardRequest request
    ) {
        CardResponse response = cardService.saveCard(request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Insert multiple cards via TXT file",
            description = "Registers multiple card numbers from a TXT file. Each line must contain one card number.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cards successfully registered"),
                    @ApiResponse(responseCode = "400", description = "Invalid file format or content")
            }
    )
    @PostMapping("/upload")
    public ResponseEntity<String> uploadCardsFile(
            @Parameter(description = "TXT file containing card numbers, one per line")
            @RequestParam("file") MultipartFile file
    ) {
        cardService.saveCardsFromFile(file);
        return ResponseEntity.ok("Cards uploaded successfully");
    }

    @Operation(
            summary = "Check if a card exists",
            description = "Checks if a full card number exists in the database and returns its unique identifier.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Card found",
                            content = @Content(schema = @Schema(implementation = CardResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Card not found")
            }
    )
    @GetMapping("/{cardNumber}")
    public ResponseEntity<CardResponse> checkCard(
            @Parameter(description = "Full card number to be checked")
            @PathVariable String cardNumber
    ) {
        CardResponse response = cardService.findCard(cardNumber);
        return ResponseEntity.ok(response);
    }

}