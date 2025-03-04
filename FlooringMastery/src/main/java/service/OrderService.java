package service;

import dto.Order;
import dto.Product;
import dto.Tax;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    int generateOrderNumber();
    List<Order> getOrders(LocalDate date);
    void addOrder();
    void editOrder();
    void removeOrder();
    void exportData();
    Order calculateOrderCost();
    BigDecimal calculateMaterialCost();
    BigDecimal calculateLaborCost();
    BigDecimal calculateTax();
    BigDecimal calculateTotal();
    boolean validateOrder();
    Product getProductByType(String type);
    List<Product> getAllProducts();
    Tax getTaxByState(String state);
}
