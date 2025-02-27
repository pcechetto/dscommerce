package com.example.dscommerce.dto;

import com.example.dscommerce.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductDTO {

    private Long id;

    @Size(min = 2, max = 80, message = "Product name must be between 2 and 80 characters")
    @NotBlank(message = "Product name required")
    private String name;

    @Size(min = 10, message = "Product description must be at least 10 characters")
    private String description;

    @Positive(message = "Product price must be greater than 0")
    private Double price;

    private String imageUrl;

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, String description, Double price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public ProductDTO(Product entity) {
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
        price = entity.getPrice();
        imageUrl = entity.getImgUrl();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
