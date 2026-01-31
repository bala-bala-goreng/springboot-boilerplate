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
@Table(name = "bank_accounts", schema = "account")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class BankAccount {

    @Id
    @Column(name = "id", length = 255)
    private String id;

    @Column(name = "account_number", length = 50, unique = true, nullable = false)
    private String accountNumber;

    @Column(name = "account_name", length = 255, nullable = false)
    private String accountName;

    @Column(name = "bank_code", length = 10, nullable = false)
    private String bankCode;

    @Column(name = "bank_name", length = 255, nullable = false)
    private String bankName;

    @Column(name = "account_type", length = 50, nullable = false)
    private String accountType;

    @Column(name = "balance", precision = 18, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(name = "currency", length = 3)
    private String currency = "IDR";

    @Column(name = "status", length = 20)
    private String status = "ACTIVE";

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
