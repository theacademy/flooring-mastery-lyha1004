package controller;

import dao.*;
import dto.Order;
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
    private OrderService service = new OrderServiceImpl();

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;
        while (keepGoing) {

            menuSelection = getMenuSelection();

            switch (menuSelection) {
                case 1:
                    io.print("Display");
                    break;
                case 2:
                    io.print("Add");
                    createOrder();
                    break;
                case 3:
                    io.print("Edit");
                    break;
                case 4:
                    io.print("Remove");
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
                LocalDate orderDate = null;
                while (orderDate == null) {
                    try {
                        String orderDateString = io.readString("Enter order date (MM/dd/yyyy): ");
                        orderDate = service.validateDate(orderDateString);
                        if (orderDate.isBefore(LocalDate.now())) {
                            throw new IllegalArgumentException("Order Date must be in the future.");
                        }
                    } catch (DateTimeParseException e) {
                        io.print("Invalid date format. Please try again using MM/dd/yyyy");
                    } catch (IllegalArgumentException e) {
                        io.print(e.getMessage());
                        orderDate = null;
                    }
                }


                String customerName = null;
                while (customerName == null) {
                    try {
                        customerName = service.validateName(io.readString("Enter customer name: "));
                    } catch (IllegalArgumentException e) {
                        io.print("Invalid name. Please try again. Characters are limited to [a-z][0-9] as well as periods and commas.");
                    }
                }

                String state = null;
                while (state == null) {
                    try {
                        state = service.validateState(io.readString("Enter State: "));
                    } catch (IllegalArgumentException e) {
                        io.print("Invalid state. Please try again.");
                    }
                }

                String productType = null;
                while (productType == null) {
                    try {
                        productType = service.validateProductType(io.readString("Enter Product Type: "));
                    } catch (IllegalArgumentException e) {
                        io.print("Invalid product type. Please try again.");
                    }
                }

                BigDecimal area = null;
                while (area == null) {
                    try {
                        area = service.validateArea(io.readBigDecimal("Enter Area: "));
                    } catch (NumberFormatException e) {
                        io.print("Invalid area. Please try again.");
                    } catch (IllegalArgumentException e) {
                        io.print(e.getMessage());
                    }
                }
                Order newOrder = new Order();
                newOrder.setOrderDate(orderDate);
                newOrder.setCustomerName(customerName);
                newOrder.setState(state);
                newOrder.setProductType(productType);
                newOrder.setArea(area);

                service.addOrder(newOrder);

                if (view.displayOrderSummaryAndIfOrdered(newOrder)) {
                    view.displayCreateSuccessBanner();
                }
                validOrder = true;
            } catch (DataValidationException e) {
                io.print("Error: " + e.getMessage());
            }
        }

    }

    private void displayOrders() {

    }

    private void editOrder() {

    }

    private void removeOrder() {

    }

    private void exportData() {

    }

    private void quitMessage() {

    }

}
