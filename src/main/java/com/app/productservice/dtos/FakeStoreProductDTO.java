package com.app.productservice.dtos;

import com.app.productservice.models.ProductCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreProductDTO {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private String category;
}
