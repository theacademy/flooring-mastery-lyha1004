package com.sg.flooringmastery.dao;

import dao.OrderDao;
import dao.OrderDaoImpl;
import dao.ProductDaoImpl;
import dto.Order;
import dto.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
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

    @Test
    public void testMarshallOrder() {
        Order order = new Order();
        order.setOrderNumber(1);
        order.setOrderDate(LocalDate.of(2023, 10, 1));
        order.setCustomerName("John Doe");
        order.setState("CA");
        order.setTaxRate(new BigDecimal("7.25"));
        order.setCostPerSquareFoot("5.15");
        order.setLaborCostPerSquareFoot("4.75");
        order.setProductType("Wood");
        order.setArea(new BigDecimal("100.00"));
        order.setMaterialCost(new BigDecimal("515.00"));
        order.setLaborCost(new BigDecimal("475.00"));
        order.setTax(new BigDecimal("71.25"));
        order.setTotal(new BigDecimal("1061.25"));

        String expected = "1,John Doe,CA,7.25,Wood,100.00,5.15,4.75,515.00,475.00,71.25,1061.25";
        String result = orderDao.marshallOrder(order);

        assertEquals(expected, result);
    }
}
