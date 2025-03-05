package dao;

import dto.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    private static final String PRODUCT_FILE = "Products.txt";
    private static final String DELIMITER = ",";

    @Override
    public List<Product> readProducts() {
        return new ArrayList<>();
    }

    @Override
    public Product getProduct(String productType) {
        // Implementation here
        return null;
    }
}