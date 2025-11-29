package com.example.hyperativa_back_end.controllers;

import com.example.hyperativa_back_end.dtos.AuthRequest;
import com.example.hyperativa_back_end.services.UserService;
import io.jsonwebtoken.Jwts;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.util.Date;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public class AuthController {

    private final Key key;
    private final UserService userService;

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticates the user and returns a valid JWT token")
    public ResponseEntity<String> login(@RequestBody AuthRequest request) {
        boolean authenticated = userService.authenticate(request.getUsername(), request.getPassword());

        if (authenticated) {
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
                .signWith(key)
                .compact();
    }
}