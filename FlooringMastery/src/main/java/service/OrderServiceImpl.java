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

    public OrderServiceImpl(OrderDao orderDao, ProductDao productDao, TaxDao taxDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.taxDao = taxDao;
    }

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
        validateOrder(order);
//        order.setOrderNumber(generateOrderNumber());
//        order = calculateOrderCost(order);
        orderDao.addOrder(order);
    }

    @Override
    public void validateOrder(Order order) {
        validateDate((order.getOrderDate().toString()));
        validateName(order.getCustomerName());
        validateState(order.getState());
        validateProductType(order.getProductType());
        validateArea(order.getArea());
    }

    @Override
    public LocalDate validateDate(String date) {
        try {
            LocalDate orderDate = LocalDate.parse(date);
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
        List<Product> products = productDao.readProducts();
        boolean productAvail = products.stream().anyMatch(product -> product.getProductType().equalsIgnoreCase(productType));
        if (!productAvail) {
            throw new IllegalArgumentException("We do not have this product available. Please look at our list and enter an available product.2");
        }
        return productType;
    }

    @Override
    public BigDecimal validateArea(BigDecimal area) {
        try {
            if (area == null || area.compareTo(BigDecimal.valueOf(100)) < 0) {
                throw new IllegalArgumentException("Area must be at least 100 sq ft.");
            }
            return area;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid area. Please enter a valid number.");
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




}