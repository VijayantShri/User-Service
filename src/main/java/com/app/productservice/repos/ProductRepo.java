package com.app.productservice.repos;

import com.app.productservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long id);
    List<Product> findAll();
}
