package dao;

import dto.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface OrderDao {
    List<Order> getOrders(LocalDate date);
    void addOrder(Order order);
    int getNextOrderNumber();
    void editOrder(LocalDate date, List<Order> order);
    void removeOrder(int orderNumber, LocalDate date);
    void exportAllData();
    void previewOrder(Order order);
}
