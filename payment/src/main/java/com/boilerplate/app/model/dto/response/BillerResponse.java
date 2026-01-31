package com.boilerplate.app.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillerResponse {
    private String id;
    private String billerCode;
    private String billerName;
    private String category;
    private String description;
    private String iconUrl;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
