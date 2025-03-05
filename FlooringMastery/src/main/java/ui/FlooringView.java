package ui;

import dto.Order;
import dto.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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


    public Order getNewOrderInfo(List<Product> products) {

        String orderDateString = io.readString("Enter the order date:");
        LocalDate orderDate = LocalDate.parse(orderDateString);
        String customerName = io.readString("Enter customer name:");
        String state = io.readString("Enter customer state abbreviation:");
        displayProductList(products);
        String productType = io.readString("Please Select a Product from the list above: ");
        String areaString = io.readString("Enter the area:");

        Order order = new Order();
        order.setOrderDate(orderDate);
        order.setCustomerName(customerName);
        order.setState(state);
        order.setProductType(productType);
        order.setArea(new BigDecimal(areaString));

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

    public void displayProductList(List<Product> products) {
        for (Product product : products) {
            io.print("Product Type: " + product.getProductType());
            io.print("Cost per Square Foot: " + product.getCostPerSquareFoot());
            io.print("Labor Cost per Square Foot" + product.getLaborCostPerSquareFoot());
            io.print("-------------------------------");
        }
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


    public void displayCreateSuccessBanner() {
        io.print("Order successfully placed.");
    }

    public void displayErrorMessage(String msg) {
        io.print(msg);
    }
}
