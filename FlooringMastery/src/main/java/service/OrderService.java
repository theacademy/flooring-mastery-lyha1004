package service;

import dto.Order;
import dto.Product;
import dto.Tax;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    int generateOrderNumber();
    List<Order> getOrders(LocalDate date);
    Order getOrder(int orderNumber, LocalDate orderDate);
    void addOrder(Order order);
    void removeOrder(int orderNumber, LocalDate orderDate);
    void editOrder(Order existingOrder) throws OrderNotFoundException;
    Order previewOrder(Order order);
    void exportData(LocalDate date) throws FlooringPersistenceException;
    void exportAllData() throws FlooringPersistenceException;

    Product getProductByType(String type) throws DataValidationException;
    List<Product> getAllProducts();
    Tax getStateTax(String state) throws DataValidationException;
    Order calculateOrderCost(Order order) throws DataValidationException;
    BigDecimal calculateMaterialCost(Order order);
    BigDecimal calculateLaborCost(Order order);
    BigDecimal calculateTax(Order order);
    BigDecimal calculateTotal(Order order);

    LocalDate validateDate(String date) throws DataValidationException;
    String validateName(String customerName) throws DataValidationException;
    String validateState(String state) throws DataValidationException;
    String validateProductType(String productType) throws DataValidationException;
    BigDecimal validateArea(String area) throws DataValidationException;

}
