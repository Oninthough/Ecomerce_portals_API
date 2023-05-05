package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByProductProductIdAndUserId(Long productId, Long userId);
    List<Review> findAllByProductProductId(Long productId);


    // Add custom query methods if needed
}
