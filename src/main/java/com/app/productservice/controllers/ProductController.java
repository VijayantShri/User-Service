package com.app.productservice.controllers;

import com.app.productservice.dtos.ExceptionDTO;
import com.app.productservice.exceptions.ProductNotFoundException;
import com.app.productservice.models.Product;
import com.app.productservice.security.services.AuthenticationService;
import com.app.productservice.services.BaseProductService;
import com.app.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private BaseProductService productService;
    private AuthenticationService authenticationService;

    // Constructor injection
    @Autowired
    public ProductController(
            @Qualifier("SelfProductService") BaseProductService productService,
            AuthenticationService authenticationService) {
        this.productService = productService;
        this.authenticationService = authenticationService;
    }

    // Setter injection - Rarely used.
//    @Autowired
//    public void setProductService(BaseProductService productService) {
//        this.productService = productService;
//    }

    @GetMapping("/{id}")
    public Product getProductById(
            @RequestHeader String token,
            @PathVariable("id") Long id)  throws ProductNotFoundException, AccessDeniedException {
//        if (!authenticationService.authenticate(token)) {
//            throw new AccessDeniedException("You are not authorised");
//        }
        return productService.getProductById(id);
    }

    @GetMapping()
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping()
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @DeleteMapping("/{id}")
    public Product deleteProduct(@PathVariable Long id)  throws ProductNotFoundException {
        return productService.deleteProductById(id);
    }

    @PutMapping("/{id}")
    public Product updatedProduct(@PathVariable("id") Long id, @RequestBody Product updatedProduct) throws ProductNotFoundException {
        return productService.updateProduct(id, updatedProduct);
    }
//    @ExceptionHandler(ProductNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ResponseBody
//    private ExceptionDTO handleProductNotFoundException(ProductNotFoundException e) {
//        ExceptionDTO exceptionDTO = new ExceptionDTO();
//        exceptionDTO.setMessage(e.getMessage());
//        exceptionDTO.setStatus("Failure");
//        return exceptionDTO;
//    }

}


/*
    1. GetProductById(id)
    2. GetAllProducts()
    3. UpdateProductById(id)
    4. DeleteProduct(id)
    5. AddProduct()
 */