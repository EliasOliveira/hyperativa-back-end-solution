package com.example.hyperativa_back_end.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Request containing card information")
public class CardRequest {

    @NotBlank
    @Schema(description = "Full card number")
    private String cardNumber;

}

