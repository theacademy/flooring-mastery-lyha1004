package dao;

import dto.Order;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderDaoImpl implements OrderDao{
    //private static final String ORDER_FILE = "Orders.txt";
    //private static final String DELIMITER = ",";
    private Map<Integer, Order> orders = new HashMap<>();
    private int nextOrderNumber = 1;

    @Override
    public List<Order> getOrders(LocalDate date) {
        return null;
    }

    @Override
    public void addOrder(Order order) {
        orders.put(order.getOrderNumber(), order);
    }

    @Override
    public int getNextOrderNumber() {
        return nextOrderNumber++;
    }

    @Override
    public void editOrder(Order order) {

    }

    @Override
    public void removeOrder(int orderNumber, LocalDate date) {

    }

    @Override
    public void exportAllData() {

    }

}
