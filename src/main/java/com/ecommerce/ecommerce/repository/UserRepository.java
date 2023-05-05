package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Add custom query methods if needed
    Optional<User> findByEmail(String email);
    Optional<User> findByFirstNameOrEmail(String firstName, String email);
    Optional<User> findByFirstName(String firstName);
    Boolean existsByFirstName(String firstName);
    Boolean existsByEmail(String email);
}
