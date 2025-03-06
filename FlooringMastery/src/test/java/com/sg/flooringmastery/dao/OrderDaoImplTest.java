package com.sg.flooringmastery.dao;

import dao.OrderDao;
import dao.OrderDaoImpl;
import dto.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderDaoImplTest {

    private OrderDao orderDao;

    @BeforeEach
    public void setUp() {
        orderDao = new OrderDaoImpl();
    }

    @Test
    public void testAddAndGetOrders() {
        LocalDate date = LocalDate.now();
        Order order = new Order();
        order.setOrderNumber(1);
        order.setOrderDate(date);

        orderDao.addOrder(order);
        List<Order> orders = orderDao.getOrders(date);

        assertEquals(1, orders.size());
        assertEquals(order, orders.get(0));
    }

    @Test
    public void testEditOrder() {
        LocalDate date = LocalDate.now();
        Order order = new Order();
        order.setOrderNumber(1);
        order.setOrderDate(date);

        orderDao.addOrder(order);
        List<Order> orders = orderDao.getOrders(date);
        assertEquals(1, orders.size());

        order.setCustomerName("Updated Name");
        orderDao.editOrder(date, orders);

        List<Order> updatedOrders = orderDao.getOrders(date);
        assertEquals("Updated Name", updatedOrders.get(0).getCustomerName());
    }

    @Test
    public void testRemoveOrder() {
        LocalDate date = LocalDate.now();
        Order order = new Order();
        order.setOrderNumber(1);
        order.setOrderDate(date);

        orderDao.addOrder(order);
        List<Order> orders = orderDao.getOrders(date);
        assertEquals(1, orders.size());

        orderDao.removeOrder(1, date);
        orders = orderDao.getOrders(date);
        assertTrue(orders.isEmpty());
    }
}
