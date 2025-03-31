package com.example.dscommerce.dto;

import com.example.dscommerce.entities.Product;

public class ProductMinDTO {

    private Long id;
    private String name;
    private Double price;
    private String imageUrl;

    public ProductMinDTO() {
    }

    public ProductMinDTO(Long id, String name, Double price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public ProductMinDTO(Product entity) {
        id = entity.getId();
        name = entity.getName();
        price = entity.getPrice();
        imageUrl = entity.getImgUrl();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
