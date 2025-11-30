package com.example.hyperativa_back_end.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Schema(description = "Request containing card information")
@AllArgsConstructor
public class CardRequest {

    @NotBlank
    @Size(min = 24, max = 26)
    @Schema(description = "Full card number")
    private String cardNumber;

}

