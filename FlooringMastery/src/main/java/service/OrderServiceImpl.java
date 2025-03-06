package service;


import dao.*;
import dto.Order;
import dto.Product;
import dto.Tax;
import ui.UserIO;
import ui.UserIOImpl;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao = new OrderDaoImpl();
    private ProductDao productDao = new ProductDaoImpl();
    private TaxDao taxDao = new TaxDaoImpl();
    private UserIO io = new UserIOImpl();
    private static int orderCounter = 1;

    public OrderServiceImpl(OrderDao orderDao, ProductDao productDao, TaxDao taxDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.taxDao = taxDao;
    }

    @Override
    public int generateOrderNumber() {
        return orderCounter++;
    }

    @Override
    public List<Order> getOrders(LocalDate date) {
        return orderDao.getOrders(date);
    }

    @Override
    public Order getOrder(int orderNumber, LocalDate orderDate) {
        List<Order> orders = orderDao.getOrders(orderDate);
        return orders.stream()
                .filter(order -> order.getOrderNumber() == orderNumber)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void addOrder(Order order) {
        orderDao.addOrder(order);
    }

    @Override
    public void removeOrder(int orderNumber, LocalDate orderDate) {
        orderDao.removeOrder(orderNumber, orderDate);
    }

    @Override
    public void editOrder(Order order) {
        List<Order> orders = orderDao.getOrders(order.getOrderDate());
        orders.removeIf(o -> o.getOrderNumber() == order.getOrderNumber());
        orders.add(order);
        orderDao.editOrder(order.getOrderDate(), orders);
    }

    @Override
    public void previewOrder(Order order) {
        validateOrder(order);
        order.setOrderNumber(generateOrderNumber());
        order = calculateOrderCost(order);

    }

    @Override
    public Order updateOrder(Order existingOrder, Order updatedOrder) {
        existingOrder.updateFields(updatedOrder);
        return calculateOrderCost(existingOrder);
    }

    @Override
    public void exportData(LocalDate date) {
        List<Order> orders = orderDao.getOrders(date);
        if (orders.isEmpty()) {
            io.print("No orders found for the specified date.");
            return;
        }

        String fileName = "Orders_" + date.format(DateTimeFormatter.ofPattern("MMddyyyy")) + ".txt";
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName))) {
            out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total");
            for (Order order : orders) {
                String marshalledOrder = orderDao.marshallOrder(order);
                out.println(marshalledOrder);
            }
            io.print("Orders successfully exported to " + fileName);
        } catch (IOException e) {
            io.print("Error exporting orders: " + e.getMessage());
        }
    }

    @Override
    public Product getProductByType(String type) {
        Product product = productDao.getProduct(type);
        if (product == null) {
            throw new DataValidationException("Product type not found: " + type);
        }
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        return productDao.readProducts();
    }

    @Override
    public Tax getStateTax(String state) {
        return taxDao.getStateTax(state);
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
                throw new DataValidationException("Order Date must be in the future.");
            }
            return orderDate;
        } catch (DateTimeParseException e) {
            throw new DataValidationException("Invalid input. Please enter a valid date in MM/dd/yyyy format.");
        }
    }

    @Override
    public String validateName(String customerName) {
        if (customerName == null || !customerName.matches("[a-zA-Z0-9., ]+")) {
            throw new DataValidationException("Invalid Customer Name.");
        }
        return customerName;
    }

    @Override
    public String validateState(String state) {
        List<Tax> taxes = taxDao.getAllTaxInfo();
        boolean stateExists = taxes.stream()
                .anyMatch(tax -> tax.getStateAbbreviation().equalsIgnoreCase(state));
        if (!stateExists) {
            throw new DataValidationException("Sorry, we cannot sell to this state.");
        }
        return state;
    }

    @Override
    public String validateProductType(String productType) {
        List<Product> products = productDao.readProducts();
        boolean productAvail = products.stream().anyMatch(product -> product.getProductType().equalsIgnoreCase(productType));
        if (!productAvail) {
            throw new DataValidationException("We do not have this product available. Please look at our list and enter an available product.2");
        }
        return productType;
    }

    @Override
    public BigDecimal validateArea(BigDecimal area) {
        try {
            if (area == null || area.compareTo(BigDecimal.valueOf(100)) < 0) {
                throw new DataValidationException("Area must be at least 100 sq ft.");
            }
            return area;
        } catch (NumberFormatException e) {
            throw new DataValidationException("Invalid area. Please enter a valid number.");
        }

    }

    @Override
    public Order calculateOrderCost(Order order) {
        try {
            BigDecimal materialCost = calculateMaterialCost(order);
            BigDecimal laborCost = calculateLaborCost(order);
            BigDecimal tax = calculateTax(order);
            BigDecimal total = calculateTotal(order);

            order.setMaterialCost(materialCost);
            order.setLaborCost(laborCost);
            order.setTax(tax);
            order.setTotal(total);

            return order;
        } catch (Exception e) {
            throw new DataValidationException("Error calculating order cost", e);
        }
    }

    @Override
    public BigDecimal calculateMaterialCost(Order order) {
        Product product = getProductByType(order.getProductType());
        return product.getCostPerSquareFoot().multiply(order.getArea()).setScale(2, RoundingMode.HALF_UP);
    }


    @Override
    public BigDecimal calculateLaborCost(Order order) {
        Product product = getProductByType(order.getProductType());
        return product.getLaborCostPerSquareFoot().multiply(order.getArea()).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal calculateTax(Order order) {
        BigDecimal materialCost = calculateMaterialCost(order);
        BigDecimal laborCost = calculateLaborCost(order);
        Tax tax = getStateTax(order.getState());
        BigDecimal subtotal = materialCost.add(laborCost);
        return subtotal.multiply(tax.getTaxRate().divide(BigDecimal.valueOf(100))).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal calculateTotal(Order order) {
        BigDecimal materialCost = calculateMaterialCost(order);
        BigDecimal laborCost = calculateLaborCost(order);
        BigDecimal tax = calculateTax(order);
        return materialCost.add(laborCost).add(tax).setScale(2, RoundingMode.HALF_UP);
    }

}