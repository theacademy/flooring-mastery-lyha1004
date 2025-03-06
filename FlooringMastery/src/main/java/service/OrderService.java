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
    void editOrder(Order updatedOrder);
    void removeOrder(int orderNumber, LocalDate orderDate);
    void exportData();
    Order calculateOrderCost(Order order);
    BigDecimal calculateMaterialCost(Order order);
    BigDecimal calculateLaborCost(Order order);
    BigDecimal calculateTax(Order order);
    BigDecimal calculateTotal(Order order);

    void previewOrder(Order order);

    void validateOrder(Order order);
    Product getProductByType(String type);
    List<Product> getAllProducts();
    Tax getTaxByState(String state);

    //validations
    LocalDate validateDate(String date);
    String validateName(String customerName);
    String validateState(String state);
    String validateProductType(String productType);
    BigDecimal validateArea(BigDecimal area);

    Order getOrder(int orderNumber, LocalDate orderDate);

}
