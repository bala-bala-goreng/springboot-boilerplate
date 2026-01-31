package com.boilerplate.app.controller;

import com.boilerplate.app.model.dto.request.InquiryRequest;
import com.boilerplate.app.model.dto.request.PaymentRequest;
import com.boilerplate.app.model.dto.response.BillerResponse;
import com.boilerplate.app.model.dto.response.InquiryResponse;
import com.boilerplate.app.model.dto.response.PaymentResponse;
import com.boilerplate.app.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Tag(name = "Payment", description = "Payment management APIs")
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(
        summary = "Get list of billers",
        description = "Retrieves a list of all active biller partners that can be used for payment"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of billers"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value = "/billers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BillerResponse>> getBillers() {
        log.info("Received request to get all billers");
        List<BillerResponse> billers = paymentService.getAllBillers();
        return ResponseEntity.ok(billers);
    }

    @Operation(
        summary = "Inquiry payment data",
        description = "Inquires payment information for a specific biller and customer number"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved inquiry data"),
        @ApiResponse(responseCode = "400", description = "Invalid request"),
        @ApiResponse(responseCode = "404", description = "Biller not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/inquiry", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InquiryResponse> inquiry(@Valid @RequestBody InquiryRequest request) {
        log.info("Received inquiry request: billerCode={}, customerNumber={}", 
                request.getBillerCode(), request.getCustomerNumber());
        InquiryResponse response = paymentService.inquiry(request);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Process payment",
        description = "Processes a payment transaction for a biller"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Payment processed successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request"),
        @ApiResponse(responseCode = "404", description = "Biller not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/payment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentResponse> payment(@Valid @RequestBody PaymentRequest request) {
        log.info("Received payment request: billerCode={}, customerNumber={}, amount={}", 
                request.getBillerCode(), request.getCustomerNumber(), request.getAmount());
        PaymentResponse response = paymentService.processPayment(request);
        return ResponseEntity.ok(response);
    }
}
