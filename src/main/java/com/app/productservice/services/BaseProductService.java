package com.app.productservice.services;

import com.app.productservice.exceptions.ProductNotFoundException;
import com.app.productservice.models.Product;

import java.util.List;

public interface BaseProductService {

    Product getProductById(Long id)  throws ProductNotFoundException;

    List<Product> getAllProducts();

    Product deleteProductById(Long id) throws ProductNotFoundException;

    Product addProduct(Product product);

    Product updateProduct(Long id, Product updatedProduct) throws ProductNotFoundException;
}
