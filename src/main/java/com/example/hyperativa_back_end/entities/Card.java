package com.example.hyperativa_back_end.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "cards")
public class Card {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id = UUID.randomUUID();

    // Hash of full PAN (e.g., SHA-256 with app-level pepper + per-record salt)
    @Column(name = "pan_hash", nullable = false, length = 128, unique = true)
    private String panHash;

    // Per-record random salt used when computing panHash (hex/base64)
    @Column(name = "salt", nullable = false, length = 64)
    private String salt;

    // Optional: a tokenized identifier returned to clients (not the PAN!)
    @Column(name = "token", nullable = false, unique = true, length = 64)
    private String token;

    // For UX/debug: last 4 digits only (never store full PAN)
    @Column(name = "last4", nullable = false, length = 4)
    private String last4;

    @Column(name = "brand", length = 50)
    private String brand;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

}