package com.boilerplate.app.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InquiryRequest {
    @NotBlank(message = "Biller code is required")
    private String billerCode;

    @NotBlank(message = "Customer number is required")
    private String customerNumber;
}
