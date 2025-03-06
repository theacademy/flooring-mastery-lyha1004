package controller;

import dao.*;
import dto.Order;
import dto.Product;
import org.springframework.cglib.core.Local;
import service.DataValidationException;
import java.time.format.DateTimeParseException;
import service.OrderService;
import service.OrderServiceImpl;
import ui.FlooringView;
import ui.UserIO;
import ui.UserIOImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FlooringController {

    private FlooringView view = new FlooringView();
    private OrderDao orderDao = new OrderDaoImpl();
    private ProductDao productDao = new ProductDaoImpl();
    private TaxDao taxDao = new TaxDaoImpl();
    private UserIO io = new UserIOImpl();
    private OrderService service = new OrderServiceImpl(orderDao, productDao, taxDao);

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;
        while (keepGoing) {

            menuSelection = getMenuSelection();

            switch (menuSelection) {
                case 1:
                    displayOrders();
                    break;
                case 2:
                    createOrder();
                    break;
                case 3:
                    editOrder();
                    break;
                case 4:
                    removeOrder();
                    break;
                case 5:
                    io.print("Export");
                    break;
                case 6:
                    keepGoing = false;
                    break;
                default:
                    io.print("UNKNOWN COMMAND");
            }
        }
        io.print("Thank you for shopping with us.");
    }

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

    private void createOrder() {
        boolean validOrder = false;
        while (!validOrder) {
            try {

                List<Product> products = productDao.readProducts();

                Order newOrder = view.getNewOrderInfo(products);

                service.previewOrder(newOrder);

                if (view.displayOrderSummaryAndIfOrdered(newOrder)) {
                    service.addOrder(newOrder);
                    view.displayCreateSuccessBanner();
                } else {
                    view.displayOrderNotPlacedBanner();
                }

                validOrder = true;

            } catch (DataValidationException | IllegalArgumentException e) {
                view.displayErrorMessage("Error: " + e.getMessage());
            }
        }

    }


    private void displayOrders() {
        LocalDate orderDate = view.getDateofOrderDisplay();
        List<Order> orders = service.getOrders(orderDate);

        if (orders.isEmpty()) {
            view.displayNoOrdersExist();
        } else {
            view.displayOrdersForDate(orders);
        }
    }

    private void editOrder() {
        LocalDate orderDate = view.getDateofOrderDisplay();
        int orderNumber = view.getOrderNumber();
        Order existingOrder = service.getOrder(orderNumber, orderDate);
        List<Product> products = productDao.readProducts();

        if (existingOrder == null) {
            view.displayOrderDoesNotExist();
        } else {
            view.displayOrderInfo(existingOrder);
            Order updatedOrder = view.getUpdatedOrderInfo(existingOrder, products);

            existingOrder = service.updateOrder(existingOrder, updatedOrder);

            if (view.displayUpdatedOrderAndIfSaved(existingOrder)) {
                service.editOrder(existingOrder);
                view.displayEditSuccessBanner();
            } else {
                view.displayOrderNotEdited();
            }
        }
    }

    private void removeOrder() {
        LocalDate orderDate = view.getDateofOrderDisplay();
        int orderNumber = view.getOrderNumber();
        Order order = service.getOrder(orderNumber, orderDate);

        if(order == null) {
            view.displayOrderDoesNotExist();
        } else {
            view.displayOrderInfo(order);
            if (view.getIfRemoveOrder()) {
                service.removeOrder(orderNumber, orderDate);
                view.displayOrderRemovedBanner();
            } else {
                view.displayOrderNotRemoved();
            }
        }
    }

    private void exportData() {

    }

    private void quitMessage() {

    }

}
