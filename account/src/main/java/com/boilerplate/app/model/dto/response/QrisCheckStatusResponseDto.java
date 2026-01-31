package com.boilerplate.app.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QrisCheckStatusResponseDto {
    
    private Boolean success;
    
    private QrisStatusData data;
    
    private String timestamp; // Format: "2025-12-03 10:24:44 +0700"
    
    @Getter
    @Setter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class QrisStatusData {
        private String id;
        private String code;
        private String acquirer;
        private String referenceNumber;
        private String merchantCode;
        private String tid;
        private String merchantName;
        private String nmid;
        private Long amount;
        private String traceNumber;
        private String status; // PENDING, PAID
        private String paidTime; // Format: "2025-12-03 10:24:44 +0700"
    }
}

