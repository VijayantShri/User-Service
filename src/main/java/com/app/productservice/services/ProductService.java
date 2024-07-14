package com.app.productservice.services;

import com.app.productservice.models.Product;
import com.app.productservice.models.ProductCategory;
import com.app.productservice.repos.CategoryRepo;
import com.app.productservice.repos.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("SelfProductService")
public class ProductService implements BaseProductService {

    private ProductRepo productRepo;
    private CategoryRepo categoryRepo;

    public ProductService(ProductRepo productRepo, CategoryRepo categoryRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public Product getProductById(Long id) {
        Optional<Product> product = productRepo.findById(id);

        if (product.isPresent()) {
            ProductCategory productCategory = product.get().getCategory();
        }

        return product.get();
    }

    @Override
    public List<Product> getAllProducts() {
        return null;
    }

    @Override
    public Product deleteProductById(Long id) {
        return null;
    }

    @Override
    public Product addProduct(Product product) {
        Optional<ProductCategory> productCategory = this.categoryRepo.findByName(product.getCategory().getName());
        if (productCategory != null && productCategory.isPresent()) {
            product.setCategory(productCategory.get());
        } else {
            ProductCategory category = this.categoryRepo.save(product.getCategory());
            product.setCategory(category);
        }
        return this.productRepo.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product updatedProduct) {
        return null;
    }
}
