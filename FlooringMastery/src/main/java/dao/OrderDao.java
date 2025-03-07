package dao;

import dto.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface OrderDao {
    List<Order> getOrders(LocalDate date);
    Order getOrder(int orderNumber, LocalDate orderDate);
    List<Order> getAllOrders();
    void addOrder(Order order);
    void editOrder(LocalDate date, List<Order> order);
    void removeOrder(int orderNumber, LocalDate date);
    String marshallOrder(Order order);
    String marshallDate(Order order);
}
