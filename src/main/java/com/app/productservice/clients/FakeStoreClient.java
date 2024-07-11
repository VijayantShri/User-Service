package com.app.productservice.clients;

import com.app.productservice.dtos.FakeStoreProductDTO;
import com.app.productservice.exceptions.ProductNotFoundException;
import com.app.productservice.models.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Component
public class FakeStoreClient {

    private final RestTemplateBuilder restTemplateBuilder;    // Object created by spring itself.
    private final String specificProductUrl;
    private final String genericProductsUrl;

    public FakeStoreClient(
            RestTemplateBuilder restTemplateBuilder,
            @Value("${fakestore.api.specificUrl}") String specificProductUrl,
            @Value("${fakestore.api.genericUrl}") String genericProductUrl) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.specificProductUrl = specificProductUrl;
        this.genericProductsUrl = genericProductUrl;
    }

    public FakeStoreProductDTO getProductById(Long id)  throws ProductNotFoundException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDTO> responseEntity = restTemplate.getForEntity(specificProductUrl, FakeStoreProductDTO.class, id);

        if (responseEntity.getBody() == null) {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }

        return responseEntity.getBody();
    }

    public List<FakeStoreProductDTO> getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDTO[]> responseEntity = restTemplate.getForEntity(genericProductsUrl, FakeStoreProductDTO[].class);
        return List.of(Objects.requireNonNull(responseEntity.getBody()));
    }

    public FakeStoreProductDTO deleteProductById(Long id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
//        restTemplate.delete(specificProductUrl, id);
        RequestCallback requestCallback = restTemplate.acceptHeaderRequestCallback(FakeStoreProductDTO.class);
        ResponseExtractor<ResponseEntity<FakeStoreProductDTO>> responseExtractor =
                restTemplate.responseEntityExtractor(FakeStoreProductDTO.class);
        ResponseEntity<FakeStoreProductDTO> responseEntity =
                restTemplate.execute(specificProductUrl, HttpMethod.DELETE, requestCallback, responseExtractor, id);

        return Objects.requireNonNull(responseEntity).getBody();
    }

    public FakeStoreProductDTO addProduct(FakeStoreProductDTO fakeStoreProductDTO) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDTO> responseEntity =
                restTemplate.postForEntity(
                        genericProductsUrl,
                        fakeStoreProductDTO,
                        FakeStoreProductDTO.class
                );
        return responseEntity.getBody();
    }

    public FakeStoreProductDTO updateProduct(FakeStoreProductDTO updatedFakeStoreProductDTO) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        String url = genericProductsUrl + "/" + updatedFakeStoreProductDTO.getId();
        HttpEntity<FakeStoreProductDTO> requestUpdate = new HttpEntity<>(updatedFakeStoreProductDTO);
        ResponseEntity<FakeStoreProductDTO> responseEntity = restTemplate.exchange(
                url, HttpMethod.PUT, requestUpdate, FakeStoreProductDTO.class
        );
        return responseEntity.getBody();
    }
}
