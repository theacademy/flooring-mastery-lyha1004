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
                    io.print("Display");
                    break;
                case 2:
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

                List<Product> products = productDao.readProducts();

                Order newOrder = view.getNewOrderInfo(products);
                service.addOrder(newOrder);

                if (view.displayOrderSummaryAndIfOrdered(newOrder)) {
                    view.displayCreateSuccessBanner();
                }

                validOrder = true;

            } catch (DataValidationException | IllegalArgumentException e) {
                view.displayErrorMessage("Error: " + e.getMessage());
            }
        }

    }

//    private void displayProducts() {
//        List<Product> products = productDao.readProducts();
//        view.displayProductList(products);
//    }

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
