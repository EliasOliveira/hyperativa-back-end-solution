package com.example.hyperativa_back_end.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.UUID;

@Data
@Schema(description = "Response containing card information")
public class CardResponse {
    @Schema(description = "Unique identifier of the card")
    private UUID id;

    @Schema(description = "Full card number")
    private String cardNumber;
}
