package controller;


import dao.*;
import dto.Order;
import dto.Product;
import dto.Tax;
import org.springframework.cglib.core.Local;
import service.DataValidationException;
import service.OrderService;
import service.OrderServiceImpl;
import ui.FlooringView;
import ui.UserIO;
import ui.UserIOImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class FlooringController {

    private UserIO io = new UserIOImpl();
    private FlooringView view = new FlooringView(io);
    private OrderDao orderDao = new OrderDaoImpl();
    private ProductDao productDao = new ProductDaoImpl();
    private TaxDao taxDao = new TaxDaoImpl();
    private OrderService service = new OrderServiceImpl(orderDao, productDao, taxDao);

    public FlooringController (OrderService service, FlooringView view) {
        this.service = service;
        this.view = view;
    }

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
                    addOrder();
                    break;
                case 3:
                    editOrder();
                    break;
                case 4:
                    removeOrder();
                    break;
                case 5:
                    exportData();
                    break;
                case 6:
                    exportAllData();
                    break;
                case 7:
                    keepGoing = false;
                    break;
                default:
                    view.displayUnknown();
            }
        }
        view.displayQuitMessage();
    }

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

    private void addOrder() {
        boolean validOrder = false;
        while (!validOrder) {
            try {
                List<Product> products = productDao.readProducts();
                Order newOrder = new Order();

                while (true) {
                    try {
                        String orderDateString = view.getOrderDateString();
                        LocalDate orderDate = service.validateDate(orderDateString);
                        newOrder.setOrderDate(orderDate);
                        break;
                    } catch (DataValidationException e) {
                        view.displayErrorMessage("Invalid Date Format: " + e.getMessage());
                    }
                }

                do {
                    try {
                        String customerName = view.getCustomerName();
                        service.validateName(customerName);
                        newOrder.setCustomerName(customerName);
                        break;
                    } catch (DataValidationException e) {
                        view.displayErrorMessage("Invalid Customer Name: " + e.getMessage());
                    }
                } while (true);

                do {
                    try {
                        String state = view.getState();
                        service.validateState(state);
                        newOrder.setState(state);
                        Tax tax = service.getStateTax(state);
                        newOrder.setTaxRate(tax.getTaxRate());
                        break;
                    } catch (DataValidationException e) {
                        view.displayErrorMessage("Invalid State: " + e.getMessage());
                    }
                } while (true);

                do {
                    try {
                        String productType = view.getProductType(products);
                        service.validateProductType(productType);
                        newOrder.setProductType(productType);
                        Product product = service.getProductByType(productType);
                        newOrder.setCostPerSquareFoot(product.getCostPerSquareFoot().toString());
                        newOrder.setLaborCostPerSquareFoot(product.getLaborCostPerSquareFoot().toString());
                        break;
                    } catch (DataValidationException e) {
                        view.displayErrorMessage("Invalid Product Type: " + e.getMessage());
                    }
                } while (true);

                do {
                    try {
                        String areaString = view.getAreaString();
                        BigDecimal area = service.validateArea(areaString);
                        newOrder.setArea(area);
                        break;
                    } catch (DataValidationException e) {
                        view.displayErrorMessage("Invalid Area: " + e.getMessage());
                    }
                } while (true);


                newOrder = service.previewOrder(newOrder);

                if (view.displayOrderSummaryAndIfOrdered(newOrder)) {
                    service.addOrder(newOrder);
                    view.displayCreateSuccessBanner();
                } else {
                    view.displayOrderNotPlacedBanner();
                }

                validOrder = true;

            } catch (IllegalArgumentException e) {
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
            Order updatedOrder = new Order();
            updatedOrder.setOrderDate(orderDate);
            view.getUpdatedOrderInfo(updatedOrder, existingOrder, products);

            if (!existingOrder.getProductType().equals(updatedOrder.getProductType())) {
                Product newProduct = service.getProductByType(updatedOrder.getProductType());
                updatedOrder.setCostPerSquareFoot(newProduct.getCostPerSquareFoot().toString());
                updatedOrder.setLaborCostPerSquareFoot(newProduct.getLaborCostPerSquareFoot().toString());
            }

            Tax tax = service.getStateTax(updatedOrder.getState());
            updatedOrder.setTaxRate(tax.getTaxRate());
            updatedOrder = service.calculateOrderCost(updatedOrder);

            boolean isSaved = view.displayUpdatedOrderAndIfSaved(updatedOrder);
            if (isSaved) {
                service.editOrder(updatedOrder);
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
        LocalDate date = view.getDateofOrderDisplay();
        service.exportData(date);
        view.displayExportSuccessBanner();
    }

    private void exportAllData() {
        service.exportAllData();
        view.displayExportSuccessBanner();
    }

}
