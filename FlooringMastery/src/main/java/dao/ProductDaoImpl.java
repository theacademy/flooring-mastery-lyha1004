package dao;

import dto.Product;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDaoImpl implements ProductDao {
    private static final String PRODUCT_FILE = "/SampleFileData/Data/Products.txt";
    private static final String DELIMITER = ",";
    private Map<String, Product> products = new HashMap<>();

    public ProductDaoImpl() {
        loadProducts();
    }

    @Override
    public void loadProducts() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(PRODUCT_FILE)))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(DELIMITER);
                if (tokens.length == 3) {
                    try {
                        String productType = tokens[0];
                        BigDecimal costPerSquareFoot = new BigDecimal(tokens[1]);
                        BigDecimal laborCostPerSquareFoot = new BigDecimal(tokens[2]);

                        Product product = new Product(productType, costPerSquareFoot, laborCostPerSquareFoot);
                        products.put(productType, product);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid number format in line: " + line);
                    }
                } else {
                    System.err.println("Invalid line format: " + line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not load product data.", e);
        }
    }

    @Override
    public List<Product> readProducts() {
        return new ArrayList<>(products.values());
    }

    @Override
    public Product getProduct(String productType) {
        // Implementation here
        return null;
    }
}