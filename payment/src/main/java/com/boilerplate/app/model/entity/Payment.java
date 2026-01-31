package com.boilerplate.app.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments", schema = "payment")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Payment {

    @Id
    @Column(name = "id", length = 255)
    private String id;

    @Column(name = "transaction_id", length = 100, unique = true, nullable = false)
    private String transactionId;

    @Column(name = "biller_code", length = 50, nullable = false)
    private String billerCode;

    @Column(name = "customer_number", length = 100, nullable = false)
    private String customerNumber;

    @Column(name = "amount", precision = 18, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(name = "currency", length = 3)
    private String currency = "IDR";

    @Column(name = "status", length = 20)
    private String status = "PENDING";

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @Column(name = "inquiry_data", columnDefinition = "TEXT")
    private String inquiryData;

    @Column(name = "payment_data", columnDefinition = "TEXT")
    private String paymentData;

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (id == null) {
            id = java.util.UUID.randomUUID().toString();
        }
        if (transactionId == null) {
            transactionId = "TXN-" + System.currentTimeMillis();
        }
    }
}
