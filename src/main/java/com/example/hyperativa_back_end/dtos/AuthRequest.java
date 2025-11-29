package com.example.hyperativa_back_end.dtos;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
