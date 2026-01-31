package com.boilerplate.app.repository;

import com.boilerplate.app.model.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
    
    Optional<BankAccount> findByAccountNumber(String accountNumber);
    
    List<BankAccount> findByStatus(String status);
    
    List<BankAccount> findByBankCode(String bankCode);
}
