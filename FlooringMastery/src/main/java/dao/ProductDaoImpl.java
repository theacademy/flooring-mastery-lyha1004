package dao;

import dto.Product;

import java.util.List;

public class ProductDaoImpl implements ProductDao{
    @Override
    public List<Product> readProducts() {
        return List.of();
    }

    @Override
    public Product getProduct(String productType) {
        return null;
    }
}
