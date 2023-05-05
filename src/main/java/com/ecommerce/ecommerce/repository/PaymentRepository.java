package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // Add custom query methods if needed
    @Query("SELECT p FROM Payment p JOIN p.order o JOIN o.user u WHERE u.id = :userId")
    List<Payment> findPaymentsByUserId(Long userId);
}
