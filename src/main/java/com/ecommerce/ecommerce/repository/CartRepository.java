package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.entities.Cart;
import com.ecommerce.ecommerce.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser(User user);
    /*
    @Query("SELECT c FROM Cart c WHERE c.user = :user")
    List<Cart> findByUser(@Param("user") User user);

     */
}
