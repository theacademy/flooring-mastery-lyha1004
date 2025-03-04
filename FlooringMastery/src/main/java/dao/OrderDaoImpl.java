package dao;

import dto.Order;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao{
    private static final String ORDER_FOLDER = "Orders/";

    @Override
    public List<Order> getOrders(LocalDate date) {
        return List.of();
    }

    @Override
    public void addOrder(Order order) {

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

    @Override
    public List<Order> readOrders(String date) {
        return List.of();
    }

    @Override
    public void writeOrder(String string, List<Order> orders) {

    }
}
