package com.example.hyperativa_back_end.controllers;

import com.example.hyperativa_back_end.dtos.AuthRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;
import java.util.Date;

import static com.example.hyperativa_back_end.utils.Constants.SECRET_KEY;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // Hardcoded credentials
    private static final String DEFAULT_USERNAME = "admin";
    private static final String DEFAULT_PASSWORD = "admin123";

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest request) {
//        TODO use an service to validate credentials
        if (DEFAULT_USERNAME.equals(request.getUsername()) && DEFAULT_PASSWORD.equals(request.getPassword())) {
            String token = generateToken(request.getUsername());
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

    }

    private String generateToken(String username) {
        long expirationMillis = 1000 * 60 * 60; // 1 hour

        return Jwts.builder()
                .setSubject(username)
                .setIssuer("HyperativaAPI")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(SECRET_KEY)
                .compact();
    }

}