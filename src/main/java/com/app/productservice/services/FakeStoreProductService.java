package com.app.productservice.services;

import com.app.productservice.clients.FakeStoreClient;
import com.app.productservice.dtos.FakeStoreProductDTO;
import com.app.productservice.exceptions.ProductNotFoundException;
import com.app.productservice.models.Product;
import com.app.productservice.models.ProductCategory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service("FakeProductService")
public class FakeStoreProductService implements BaseProductService {

    private final FakeStoreClient fakeStoreClient;    // Third-party client mostly provided by third-party.

    public FakeStoreProductService(FakeStoreClient fakeStoreClient) {
        this.fakeStoreClient = fakeStoreClient;
    }

    @Override
    public Product getProductById(Long id)  throws ProductNotFoundException {
        return getProductFromFakeStoreProductDTO(fakeStoreClient.getProductById(id));
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new LinkedList<>();
        for (FakeStoreProductDTO fakeStoreProduct: fakeStoreClient.getAllProducts()) {
            Product product = getProductFromFakeStoreProductDTO(fakeStoreProduct);
            products.add(product);
        }
        return products;
    }

    @Override
    public Product deleteProductById(Long id) {
        return getProductFromFakeStoreProductDTO(fakeStoreClient.deleteProductById(id));
    }

    @Override
    public Product addProduct(Product product) {
        return getProductFromFakeStoreProductDTO(
                fakeStoreClient.addProduct(
                        getFakeStoreProductDTOFromProduct(product)
                )
        );
    }

    @Override
    public Product updateProduct(Long id, Product updatedProduct) throws ProductNotFoundException {
        Product existingProduct = this.getProductById(id);
        if (existingProduct == null) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }

        return getProductFromFakeStoreProductDTO(
                fakeStoreClient.updateProduct(
                        getFakeStoreProductDTOFromProduct(updatedProduct)
                )
        );
    }

    private Product getProductFromFakeStoreProductDTO(FakeStoreProductDTO fakeStoreProductDTO) {
        Product product = new Product();
//        product.setId(fakeStoreProductDTO.getId());
        product.setTitle(fakeStoreProductDTO.getTitle());
        product.setDescription(fakeStoreProductDTO.getDescription());
        product.setPrice(fakeStoreProductDTO.getPrice());
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName(fakeStoreProductDTO.getCategory());
        product.setCategory(productCategory);

        return product;
    }

    private FakeStoreProductDTO getFakeStoreProductDTOFromProduct(Product product) {
        FakeStoreProductDTO fakeStoreProductDTO = new FakeStoreProductDTO();
        fakeStoreProductDTO.setTitle(product.getTitle());
        fakeStoreProductDTO.setDescription(product.getDescription());
        fakeStoreProductDTO.setPrice(product.getPrice());
        fakeStoreProductDTO.setCategory(product.getCategory().getName());

        return fakeStoreProductDTO;
    }
}
