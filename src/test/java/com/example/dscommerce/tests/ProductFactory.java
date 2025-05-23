package com.example.dscommerce.tests;

import com.example.dscommerce.entities.Category;
import com.example.dscommerce.entities.Product;

public class ProductFactory {
    public static Product createProduct() {
        Category category = CategoryFactory.createCategory();
        Product product = new Product(1L, "Produto 1", "lorem ipsium lorem ipsium lorem ipsium lorem ipsium lorem ipsium lorem ipsium", 399.0, "https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg");
        product.getCategories().add(category);
        return product;
    }

    public static Product createProduct(String name) {
        Product product = createProduct();
        product.setName(name);
        return product;
    }
}
