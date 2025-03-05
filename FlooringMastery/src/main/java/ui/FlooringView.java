package ui;

import dto.Order;
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
        return LocalDate.parse(io.readString("Enter the date (yyyy-MM-dd)"));
    }

    public void displayOrdersForDate(List<Order> orders) {
        for (Order order : orders) {
            io.print(order.toString());
        }
    }

    public void displayNoOrdersExist() {
        io.print("No orders exist for the given data.");
    }


    public Order getNewOrderInfo() {
        String customerName = getCustomerName();
        String state = getState();
        String productType = getProductType();
        String areaString = getArea();
        String orderDateString = getOrderDate().toString();

        Order order = new Order();
        order.setCustomerName(customerName);
        order.setState(state);
        order.setProductType(productType);
        order.setArea(new BigDecimal(areaString));
        order.setOrderDate(LocalDate.parse(orderDateString));

        return order;
    }



    public boolean displayOrderSummaryAndIfOrdered(Order order) {
        io.print("Order Date: " + order.getOrderDate().toString());
        io.print("Customer Name: " + order.getCustomerName());
        io.print("State: " + order.getState());
        io.print("Product Type: " + order.getProductType());
        io.print("Area: " + order.getArea().toString());
        String userInput = io.readString("Do you want to place this order? (yes/no):");
        return userInput.equalsIgnoreCase("yes");
    }

    public LocalDate getOrderDate() {
        return LocalDate.parse(io.readString("Enter the order date (MM/dd/yyyy):"));
    }

    int getOrderNumber() {
        return Integer.parseInt(io.readString("Enter the order number:"));
    }

    public void displayOrderDoesNotExist() {
        io.print("Order does not exist.");
    }

    public boolean displayUpdatedOrderAndIfSaved(Order order) {
        io.print(order.toString());
        String userInput = io.readString("Do you want to save this order? (yes/no):");
        return userInput.equalsIgnoreCase("yes");
    }

    public void displayOrderInfo(Order order) {
        io.print(order.toString());
    }

    public boolean getIfRemoveOrder() {
        String userInput = io.readString("Do you want to remove this order? (yes/no):");
        return userInput.equalsIgnoreCase("yes");
    }

    public String getCustomerName() {
        return io.readString("Enter customer name:");
    }

    public String getState() {
        return io.readString("Enter customer state abbreviation:");
    }

    public String getProductType() {
        //print product list extracted from product file
        return io.readString("Enter the Product Type from the list above:");
    }

    public String getArea() {
        return io.readString("Enter the area:");
    }

    public void displayCreateSuccessBanner() {
        io.print("Order successfully placed.");
    }
}
