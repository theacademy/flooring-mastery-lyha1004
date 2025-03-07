package service;


import dao.*;
import dto.Order;
import dto.Product;
import dto.Tax;
import ui.UserIO;
import ui.UserIOImpl;

import javax.xml.crypto.Data;
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
    private static int orderCounter = 0;

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
        return orderDao.getOrder(orderNumber, orderDate);
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
    public void editOrder(Order order) throws OrderNotFoundException{
        List<Order> orders = orderDao.getOrders(order.getOrderDate());
        boolean orderFound = false;

        for (Order o : orders) {
            if (o.getOrderNumber() == order.getOrderNumber()) {
                orders.remove(o);
                orderFound = true;
                break;
            }
        }

        if (!orderFound) {
            throw new OrderNotFoundException("Order with order number " + order.getOrderNumber() + " not found.");
        }

        orders.add(order);
        orderDao.editOrder(order.getOrderDate(), orders);
    }


    @Override
    public Order previewOrder(Order order) {
        order.setOrderNumber(generateOrderNumber());
        order = calculateOrderCost(order);
        return order;
    }

    @Override
    public void exportData(LocalDate date) throws FlooringPersistenceException{
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
            throw new FlooringPersistenceException("Error exporting orders: ", e);
        }
    }

    @Override
    public void exportAllData() throws FlooringPersistenceException{
        List<Order> activeOrders = orderDao.getAllOrders();

        String fileName = "DataExport.txt";
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName))) {
            out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total,OrderDate");
            for (Order order : activeOrders) {
                String marshalledOrder = orderDao.marshallOrder(order);
                out.println(marshalledOrder + "," + orderDao.marshallDate(order));
            }
            io.print("Orders successfully exported to " + fileName);
        } catch (IOException e) {
            throw new FlooringPersistenceException("Error exporting orders: ", e);
        }
    }

    @Override
    public Product getProductByType(String type) throws DataValidationException{
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
    public Tax getStateTax(String state) throws DataValidationException{
        if (state == null || state.isEmpty()) {
            throw new DataValidationException("State abbreviation cannot be null or empty.");
        }
        state = state.toUpperCase();
        Tax tax = taxDao.getStateTax(state);
        if (tax == null) {
            throw new DataValidationException("State abbreviation not found: " + state);
        }
        return tax;
    }

    @Override
    public LocalDate validateDate(String date) throws DataValidationException{
        while (true) {
            try {
                LocalDate orderDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("MM-dd-yyyy"));
                if (orderDate.isBefore(LocalDate.now())) {
                    throw new DataValidationException("Order Date must be in the future.");
                }
                return orderDate;
            } catch (DateTimeParseException e) {
                throw new DataValidationException("Invalid input. Please enter a valid date in MM-dd-yyyy format.", e);
            }
        }
    }


    @Override
    public String validateName(String customerName) throws DataValidationException{
        if (customerName == null || !customerName.matches("[a-zA-Z0-9., ]+")) {
            throw new DataValidationException("Invalid Customer Name.");
        }
        return customerName;
    }

    @Override
    public String validateState(String state) throws DataValidationException{
        List<Tax> taxes = taxDao.getAllTaxInfo();
        boolean stateExists = taxes.stream()
                .anyMatch(tax -> tax.getStateAbbreviation().equalsIgnoreCase(state));
        if (!stateExists) {
            throw new DataValidationException("Sorry, we cannot sell to this state.");
        }
        return state;
    }


    @Override
    public String validateProductType(String productType) throws DataValidationException{
        List<Product> products = productDao.readProducts();
        boolean productAvail = products.stream().anyMatch(product -> product.getProductType().equalsIgnoreCase(productType));
        if (!productAvail) {
            throw new DataValidationException("We do not have this product available. Please look at our list and enter an available product.");
        }
        return productType;
    }

    @Override
    public BigDecimal validateArea(String input) throws DataValidationException{
        try {
            BigDecimal area = new BigDecimal(input);
            if (area.compareTo(BigDecimal.valueOf(100)) < 0) {
                throw new DataValidationException("Area must be at least 100 sq ft.");
            }
            return area;
        } catch (NumberFormatException e) {
            throw new DataValidationException("Invalid area. Please enter a valid number.");
        }

    }

    @Override
    public Order calculateOrderCost(Order order) throws DataValidationException {
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