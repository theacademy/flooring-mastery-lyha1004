package service;

import dao.*;
import dto.Order;
import dto.Product;
import dto.Tax;
import org.springframework.cglib.core.Local;
import ui.UserIO;
import ui.UserIOImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao = new OrderDaoImpl();
    private ProductDao productDao = new ProductDaoImpl();
    private TaxDao taxDao = new TaxDaoImpl();
    private UserIO io = new UserIOImpl();



    @Override
    public int generateOrderNumber() {
        return 0;
    }

    @Override
    public List<Order> getOrders(LocalDate date) {
        return orderDao.getOrders(date);
    }


    @Override
    public void addOrder(Order order) {
        if (validateOrder(order)) {
            order.setOrderNumber(generateOrderNumber());
            order = calculateOrderCost(order);
            orderDao.addOrder(order);
        } else {
            throw new DataValidationException("Order data is invalid.");
        }
    }


    @Override
    public void editOrder() {
    }

    @Override
    public void removeOrder() {
    }

    @Override
    public void exportData() {
    }

    @Override
    public Order calculateOrderCost(Order order) {
        return order;
    }

    @Override
    public BigDecimal calculateMaterialCost(Order order) {
        return null;
    }


    @Override
    public BigDecimal calculateLaborCost(Order order) {
        return null;
    }

    @Override
    public BigDecimal calculateTax(Order order) {
        return null;
    }

    @Override
    public BigDecimal calculateTotal(Order order) {
        return null;
    }

    @Override
    public boolean validateOrder(Order order) {
        return true;
    }

    @Override
    public Product getProductByType(String type) {
        return productDao.getProduct(type);
    }

    @Override
    public List<Product> getAllProducts() {
        return productDao.readProducts();
    }

    @Override
    public Tax getTaxByState(String state) {
        return taxDao.getTaxByState(state);
    }

@Override
public LocalDate validateDate(String date) {
    LocalDate orderDate = null;
    try {
        orderDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        if (orderDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Order Date must be in the future.");
        }
        return orderDate;
    } catch (DateTimeParseException e) {
        throw new IllegalArgumentException("Invalid input. Please enter a valid date in MM/dd/yyyy format.");
    }
}

    @Override
    public String validateName(String customerName) {
        if (customerName == null || !customerName.matches("[a-zA-Z0-9., ]+")) {
            throw new IllegalArgumentException("Invalid Customer Name.");
        }
        return customerName;
    }

    @Override
    public String validateState(String state) {
        if (state == null || state.trim().isEmpty()) {
            throw new IllegalArgumentException("State cannot be empty.");
        }
        return state;
    }

    @Override
    public String validateProductType(String productType) {
        if (productType == null || productType.trim().isEmpty()) {
            throw new IllegalArgumentException("Product Type cannot be empty.");
        }
        return productType;
    }

    @Override
    public BigDecimal validateArea(BigDecimal area) {
        if (area == null || area.compareTo(BigDecimal.valueOf(100)) < 0) {
            throw new IllegalArgumentException("Area must be at least 100 sq ft.");
        }
        return area;
    }


}