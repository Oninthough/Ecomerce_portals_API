package com.ecommerce.ecommerce.repository;


import com.ecommerce.ecommerce.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    // Add custom query methods if needed
}
