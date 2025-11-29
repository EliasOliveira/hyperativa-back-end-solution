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

    @Column(name = "number", nullable = false, length = 4)
    private String number;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

}