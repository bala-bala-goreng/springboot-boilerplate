package com.boilerplate.app.repository;

import com.boilerplate.app.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    Optional<Payment> findByTransactionId(String transactionId);
    List<Payment> findByBillerCode(String billerCode);
    List<Payment> findByStatus(String status);
}
