package com.app.productservice.repos;

import com.app.productservice.models.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepo extends JpaRepository<ProductCategory, Long> {
    Optional<ProductCategory> findByName(String name);
}
