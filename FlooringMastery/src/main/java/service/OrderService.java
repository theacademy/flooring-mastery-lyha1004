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
    Order getOrder(int orderNumber, LocalDate orderDate);
    void addOrder(Order order);
    void removeOrder(int orderNumber, LocalDate orderDate);
    void editOrder(Order existingOrder);
    Order previewOrder(Order order);
    Order updateOrder(Order existingOrder, Order updatedOrder);
    void exportData(LocalDate date);

    void exportAllData();

    Product getProductByType(String type);
    List<Product> getAllProducts();
    Tax getStateTax(String state);
    Order calculateOrderCost(Order order);
    BigDecimal calculateMaterialCost(Order order);
    BigDecimal calculateLaborCost(Order order);
    BigDecimal calculateTax(Order order);
    BigDecimal calculateTotal(Order order);

    LocalDate validateDate(String date);
    String validateName(String customerName);
    String validateState(String state);
    String validateProductType(String productType);
    BigDecimal validateArea(BigDecimal area);

}
