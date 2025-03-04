package dao;

import dto.Product;

import java.util.List;

public interface ProductDao {
    List<Product> readProducts();
    Product getProduct(String productType);
}
