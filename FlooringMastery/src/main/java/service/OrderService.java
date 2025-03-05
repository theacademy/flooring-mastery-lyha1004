package service;

import dto.Order;
import dto.Product;
import dto.Tax;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    int generateOrderNumber();
    List<Order> getOrders(LocalDate date);
    void addOrder(Order order);
    void editOrder();
    void removeOrder();
    void exportData();
    Order calculateOrderCost(Order order);
    BigDecimal calculateMaterialCost(Order order);
    BigDecimal calculateLaborCost(Order order);
    BigDecimal calculateTax(Order order);
    BigDecimal calculateTotal(Order order);
    boolean validateOrder(Order order);
    Product getProductByType(String type);
    List<Product> getAllProducts();
    Tax getTaxByState(String state);

    //validations
    LocalDate validateDate(String date);
    String validateName(String customerName);
    String validateState(String state);
    String validateProductType(String productType);
    BigDecimal validateArea(BigDecimal area);
}
