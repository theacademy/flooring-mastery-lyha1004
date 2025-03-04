package service;

import dao.OrderDao;
import dao.ProductDao;
import dao.TaxDao;
import dto.Order;
import dto.Product;
import dto.Tax;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao;
    private ProductDao productDao;
    private TaxDao taxDao;

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
        return null;
    }

    @Override
    public void addOrder() {
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
    public Order calculateOrderCost() {
        return null;
    }

    @Override
    public BigDecimal calculateMaterialCost() {
        return null;
    }

    @Override
    public BigDecimal calculateLaborCost() {
        return null;
    }

    @Override
    public BigDecimal calculateTax() {
        return null;
    }

    @Override
    public BigDecimal calculateTotal() {
        return null;
    }

    @Override
    public boolean validateOrder() {
        return false;
    }

    @Override
    public Product getProductByType(String type) {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return null;
    }

    @Override
    public Tax getTaxByState(String state) {
        return null;
    }
}