package com.boilerplate.app.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response containing the generated signature")
public class SignatureResponseDto {

    @Schema(description = "Base64-encoded HMAC-SHA512 signature", example = "base64-encoded-signature-here")
    private String signature;
}
