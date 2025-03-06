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

    public OrderDaoStubImpl(Order order) {
        this.testOrder = order;
    }

    @Override
    public List<Order> getOrders(LocalDate date) {
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
    public void editOrder(LocalDate orderDate, List<Order> updatedOrders) {
        orders = updatedOrders;
    }
}
