package com.example.hyperativa_back_end.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Schema(description = "Request to insert cards")
@AllArgsConstructor
public class CardUploadRequest {

    @Schema(description = "Base64-encoded content of the TXT file")
    @NotBlank
    private String fileBase64;

}
