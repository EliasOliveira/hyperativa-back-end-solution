package com.example.hyperativa_back_end.repositories;

import com.example.hyperativa_back_end.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, UUID> {

    Optional<Card> findByPanHash(String panHash);

    Optional<Card> findByToken(String token);

    boolean existsByPanHash(String panHash);
}