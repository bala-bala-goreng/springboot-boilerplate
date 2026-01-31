package com.boilerplate.app.service;

import com.boilerplate.app.model.dto.request.InquiryRequest;
import com.boilerplate.app.model.dto.request.PaymentRequest;
import com.boilerplate.app.model.dto.response.BillerResponse;
import com.boilerplate.app.model.dto.response.InquiryResponse;
import com.boilerplate.app.model.dto.response.PaymentResponse;
import com.boilerplate.app.model.entity.Biller;
import com.boilerplate.app.model.entity.Payment;
import com.boilerplate.app.repository.BillerRepository;
import com.boilerplate.app.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final BillerRepository billerRepository;
    private final PaymentRepository paymentRepository;

    public List<BillerResponse> getAllBillers() {
        return billerRepository.findByStatus("ACTIVE").stream()
                .map(this::mapToBillerResponse)
                .collect(Collectors.toList());
    }

    public InquiryResponse inquiry(InquiryRequest request) {
        Biller biller = billerRepository.findByBillerCode(request.getBillerCode())
                .orElseThrow(() -> new RuntimeException("Biller not found: " + request.getBillerCode()));

        // Simulate inquiry - in real implementation, this would call external biller API
        return InquiryResponse.builder()
                .billerCode(biller.getBillerCode())
                .billerName(biller.getBillerName())
                .customerNumber(request.getCustomerNumber())
                .customerName("Customer " + request.getCustomerNumber())
                .amount(new BigDecimal("100000"))
                .currency("IDR")
                .period("2024-01")
                .additionalInfo("Payment for " + biller.getBillerName())
                .build();
    }

    @Transactional
    public PaymentResponse processPayment(PaymentRequest request) {
        Biller biller = billerRepository.findByBillerCode(request.getBillerCode())
                .orElseThrow(() -> new RuntimeException("Biller not found: " + request.getBillerCode()));

        // Create payment record
        Payment payment = new Payment();
        payment.setBillerCode(request.getBillerCode());
        payment.setCustomerNumber(request.getCustomerNumber());
        payment.setAmount(request.getAmount());
        payment.setCurrency(request.getCurrency());
        payment.setStatus("SUCCESS");
        payment.setPaymentDate(LocalDateTime.now());

        payment = paymentRepository.save(payment);

        // In real implementation, this would call external payment gateway
        log.info("Payment processed: transactionId={}, billerCode={}, amount={}", 
                payment.getTransactionId(), request.getBillerCode(), request.getAmount());

        return PaymentResponse.builder()
                .transactionId(payment.getTransactionId())
                .billerCode(biller.getBillerCode())
                .billerName(biller.getBillerName())
                .customerNumber(request.getCustomerNumber())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .status(payment.getStatus())
                .paymentDate(payment.getPaymentDate())
                .message("Payment processed successfully")
                .build();
    }

    private BillerResponse mapToBillerResponse(Biller biller) {
        return BillerResponse.builder()
                .id(biller.getId())
                .billerCode(biller.getBillerCode())
                .billerName(biller.getBillerName())
                .category(biller.getCategory())
                .description(biller.getDescription())
                .iconUrl(biller.getIconUrl())
                .status(biller.getStatus())
                .createdAt(biller.getCreatedAt())
                .updatedAt(biller.getUpdatedAt())
                .build();
    }
}
