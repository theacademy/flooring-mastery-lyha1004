package com.sg.flooringmastery.dao;

import dao.ProductDao;
import dto.Product;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class ProductDaoStubImpl implements ProductDao {
    private List<Product> products = Arrays.asList(
            new Product("Wood", new BigDecimal("5.00"), new BigDecimal("4.00")),
            new Product("Tile", new BigDecimal("3.00"), new BigDecimal("2.50"))
    );

    @Override
    public Product getProduct(String type) {
        return products.stream()
                .filter(product -> product.getProductType().equalsIgnoreCase(type))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void loadProducts() {

    }

    @Override
    public List<Product> readProducts() {
        return products;
    }
}
