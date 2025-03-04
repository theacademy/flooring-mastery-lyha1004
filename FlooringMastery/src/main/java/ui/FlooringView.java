package ui;

import dto.Order;
import jdk.vm.ci.meta.Local;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class FlooringView {


    private UserIO io = new UserIOImpl();

    public int printMenuAndGetSelection() {
        io.print("<<Flooring Program>>");
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Export All Data");
        io.print("6. Quit");

        String userInput = io.readString("Enter a selection above:");
        return Integer.parseInt(userInput);
    }

    public LocalDate getDateofOrderDisplay() {
        return null;
    }

    public void displayOrdersForDate(List<Order> orders) {

    }

    public void displayNoOrdersExist() {

    }

    Order getNewOrderInfo() {
        return null;
    }

    boolean displayOrderSummaryAndIfOrdered(Order order) {

        return false;
    }

    LocalDate getOrderDate() {
        return null;
    }

    int getOrderNumber() {
        return 0;
    }

    public void displayOrderDoesNotExist() {

    }

    public boolean displayUpdatedOrderAndIfSaved(Order order) {
        return false;
    }

    public void displayOrderInfo() {

    }

    public boolean getIfRemoveOrder() {
        return false;
    }
}
