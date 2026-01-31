package com.boilerplate.app.repository;

import com.boilerplate.app.model.entity.Biller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BillerRepository extends JpaRepository<Biller, String> {
    Optional<Biller> findByBillerCode(String billerCode);
    List<Biller> findByStatus(String status);
    List<Biller> findByCategory(String category);
}
