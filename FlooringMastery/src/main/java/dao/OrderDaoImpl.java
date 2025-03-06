package dao;

import dto.Order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDaoImpl implements OrderDao{
    private static final String DELIMITER = ",";
    private Map<LocalDate, List<Order>> orders = new HashMap<>();

    @Override
    public List<Order> getOrders(LocalDate date) {
        List<Order> ordersForDate = orders.get(date);
        if (ordersForDate == null) {
            return new ArrayList<>();
        }
        return ordersForDate;
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> allOders = new ArrayList<>();
        for (List<Order> orderList : orders.values()) {
            allOders.addAll(orderList);
        }
        return allOders;
    }

    @Override
    public void addOrder(Order order) {
        List<Order> ordersForDate = orders.get(order.getOrderDate());
        if (ordersForDate == null) {
            ordersForDate = new ArrayList<>();
            orders.put(order.getOrderDate(), ordersForDate);
        }
        ordersForDate.add(order);
    }


    @Override
    public void editOrder(LocalDate date, List<Order> order) {
        orders.put(date, order);
    }

    @Override
    public void removeOrder(int orderNumber, LocalDate date) {
        List<Order> ordersForDate = orders.get(date);
        if (ordersForDate != null) {
            ordersForDate.removeIf(order -> order.getOrderNumber() == orderNumber);
        }
    }

    @Override
    public String marshallOrder(Order order) {
        return order.toString();
    }

    @Override
    public String marshallDate(Order order) {
        return order.toStringDate();
    }
}
