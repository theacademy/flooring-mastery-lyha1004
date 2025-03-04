package controller;

import dao.*;
import dto.Order;
import ui.FlooringView;
import ui.UserIO;
import ui.UserIOImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class FlooringController {

    private FlooringView view = new FlooringView();
    private OrderDao orderDao = new OrderDaoImpl();
    private ProductDao productDao = new ProductDaoImpl();
    private TaxDao taxDao = new TaxDaoImpl();
    private UserIO io = new UserIOImpl();


    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;
        while (keepGoing) {
            io.print("Main Menu");
            io.print("1. Display Orders");
            io.print("2. Add an Order");
            io.print("3. Edit an Order");
            io.print("4. Remove an Order");
            io.print("5. Export All Data");
            io.print("6. Quit");

            menuSelection = getMenuSelection();

            switch (menuSelection) {
                case 1:
                    io.print("Display");
                    break;
                case 2:
                    io.print("Add");

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

    private void displayOrders() {

    }

    private void addOrder() {

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
