package com.sg.flooringmastery.dao;

import dao.OrderDao;
import dto.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoStubImpl implements OrderDao {
    private List<Order> orders = new ArrayList<>();
    public Order testOrder;

    public OrderDaoStubImpl(){
        testOrder = new Order();
        testOrder.setOrderNumber(1);
        testOrder.setCustomerName("John Doe");
        testOrder.setState("TX");
        testOrder.setProductType("Tile");
        testOrder.setArea(new BigDecimal(249));
    }

    @Override
    public List<Order> getOrders(LocalDate date) {
        return orders;
    }

    @Override
    public Order getOrder(int orderNumber, LocalDate orderDate) {
        List<Order> orders = getOrders(orderDate);
        return orders.stream()
                .filter(order -> order.getOrderNumber() == orderNumber)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Order> getAllOrders() {
        return orders;
    }

    @Override
    public void addOrder(Order order) {
        orders.add(order);
    }

    @Override
    public void removeOrder(int orderNumber, LocalDate orderDate) {
        orders.removeIf(order -> order.getOrderNumber() == orderNumber);
    }

    @Override
    public String marshallOrder(Order order) {
        return order.toString();
    }

    @Override
    public String marshallDate(Order order) {
        return order.toStringDate();
    }

    @Override
    public void editOrder(LocalDate orderDate, List<Order> updatedOrders) {
        orders = updatedOrders;
    }
}
