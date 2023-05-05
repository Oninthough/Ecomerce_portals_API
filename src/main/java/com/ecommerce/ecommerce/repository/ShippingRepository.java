package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.entities.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShippingRepository extends JpaRepository<Shipping, Long> {
    // Add custom query methods if needed
    List<Optional<Shipping>> findByOrderOrderId(Long orderId);
    Optional<Shipping> findByOrderOrderIdAndShippingId(Long orderId, Long shippingId);
}
