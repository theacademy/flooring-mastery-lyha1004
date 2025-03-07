package com.sg.flooringmastery.dao;

import dao.ProductDao;
import dao.ProductDaoImpl;
import dto.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductDaoImplTest {

    private ProductDao productDao;

    @BeforeEach
    public void setUp() {
        productDao = new ProductDaoImpl();
    }

    @Test
    public void testReadProducts() {
        List<Product> products = productDao.readProducts();
        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertEquals(4, products.size());
    }


    @Test
    public void testGetProduct() {
        String productType = "Wood";
        Product product = productDao.getProduct(productType);
        assertNotNull(product);
        assertEquals(productType, product.getProductType());
    }

    @Test
    public void testGetNonExistentProduct() {
        String productType = "NonExistent";
        Product product = productDao.getProduct(productType);
        assertNull(product);
    }
}
