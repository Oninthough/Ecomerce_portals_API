package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Add custom query methods if needed
}
