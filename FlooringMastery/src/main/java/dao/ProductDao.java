package dao;

import dto.Product;

import java.util.List;

public interface ProductDao {

    void loadProducts();
    List<Product> readProducts() throws ProductsFileIOException;
    Product getProduct(String productType);

}
