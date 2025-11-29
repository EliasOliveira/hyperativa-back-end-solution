package com.example.hyperativa_back_end.utils;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class Constants {

    // Secret key for signing JWTs (in production, store securely)
    public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

}
