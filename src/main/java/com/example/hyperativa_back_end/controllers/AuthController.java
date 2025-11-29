package com.example.hyperativa_back_end.controllers;

import com.example.hyperativa_back_end.dtos.AuthRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest request) {
        // TODO Autentica o usuário e retorna um JWT
        return ResponseEntity.ok("JWT-TOKEN");
    }
}