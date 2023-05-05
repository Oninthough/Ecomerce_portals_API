package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    // Add custom query methods if needed
}

