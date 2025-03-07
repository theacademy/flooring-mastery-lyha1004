package ui;

import dto.Order;
import dto.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FlooringView {


    private UserIO io = new UserIOImpl();

    public FlooringView(UserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection() {
        io.print("<<Flooring Program>>");
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Export Orders");
        io.print("6. Export All Data");
        io.print("7. Quit");

        String userInput = io.readString("Enter a selection above:");
        return Integer.parseInt(userInput);
    }

    public LocalDate getDateofOrderDisplay() {
        return LocalDate.parse(io.readString("Enter the date (MM-dd-yyyy)"), DateTimeFormatter.ofPattern("MM-dd-yyyy"));
    }

    public void displayOrdersForDate(List<Order> orders) {
        io.print("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total");
        for (Order order : orders) {
            io.print(order.toString());
        }
    }

    public void displayNoOrdersExist() {
        io.print("No orders exist for the given data.");
    }

    public String getCustomerName() {
        return io.readString("Enter customer name:");
    }

    public String getOrderDateString() {
        return io.readString("Enter the order date (MM-dd-yyyy):");
    }

    public String getState() {
        return io.readString("Enter customer state abbreviation:");
    }

    public String getProductType(List<Product> products) {
        displayProductList(products);
        return io.readString("Please Select a Product from the list above: ");
    }

    public String getAreaString() {
        return io.readString("Enter the area:");
    }

    public boolean displayOrderSummaryAndIfOrdered(Order order) {
        io.print("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total");
        io.print(order.toString());

        while (true) {
            String userInput = io.readString("Do you want to save this order? (yes/no):").trim().toLowerCase();

            if (userInput.equals("yes") || userInput.equals("y")) {
                return true;
            } else if (userInput.equals("no") || userInput.equals("n")) {
                return false;
            } else {
                io.print("Invalid input. Please enter 'yes' or 'no' (or 'y'/'n').");
            }
        }
    }

    public void displayProductList(List<Product> products) {
        for (Product product : products) {
            io.print("Product Type: " + product.getProductType());
            io.print("Cost per Square Foot: " + product.getCostPerSquareFoot());
            io.print("Labor Cost per Square Foot: " + product.getLaborCostPerSquareFoot());
            io.print("-------------------------------");
        }
    }

    public int getOrderNumber() {
        return Integer.parseInt(io.readString("Enter the order number:"));
    }

    public void displayOrderDoesNotExist() {
        io.print("Order does not exist.");
    }

    public boolean displayUpdatedOrderAndIfSaved(Order order) {
        io.print("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total");
        io.print(order.toString());

        while (true) {
            String userInput = io.readString("Do you want to save this order? (yes/no):").trim().toLowerCase();

            if (userInput.equals("yes") || userInput.equals("y")) {
                return true;
            } else if (userInput.equals("no") || userInput.equals("n")) {
                return false;
            } else {
                io.print("Invalid input. Please enter 'yes' or 'no' (or 'y'/'n').");
            }
        }
    }


    public void displayOrderInfo(Order order) {
        io.print("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total");
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

    public void displayOrderNotPlacedBanner() {
        io.print("Order not made.");
    }

    public void displayOrderRemovedBanner() {
        io.print("Order has been removed.");
    }

    public void displayOrderNotRemoved() {
        io.print("Order will NOT be removed.");
    }

    public void getUpdatedOrderInfo(Order updatedOrder, Order existingOrder, List<Product> products) {
        String customerName = io.readString("Enter customer name (" + existingOrder.getCustomerName() + "): ");
        updatedOrder.setCustomerName(customerName.isEmpty() ? existingOrder.getCustomerName() : customerName);

        String state = io.readString("Enter customer state abbreviation (" + existingOrder.getState() + "): ");
        updatedOrder.setState(state.isEmpty() ? existingOrder.getState() : state);

        displayProductList(products);
        String productType = io.readString("Please Select a Product from the list above (" + existingOrder.getProductType() + "): ");
        updatedOrder.setProductType(productType.isEmpty() ? existingOrder.getProductType() : productType);

        String areaString = io.readString("Enter the area (" + existingOrder.getArea() + "): ");
        if (areaString.isEmpty()) {
            updatedOrder.setArea(existingOrder.getArea());
        } else {
            BigDecimal area = new BigDecimal(areaString);
            updatedOrder.setArea(area);
        }
    }

    public void displayEditSuccessBanner() {
        io.print("Successfully updated order.");
    }

    public void displayOrderNotEdited() {
        io.print("Order not updated.");
    }

    public void displayExportSuccessBanner() {
        io.print("Order successfully exported.");
    }

    public void displayQuitMessage() {
        io.print("Thank you for shopping with us.");
    }

    public void displayUnknown() {
        io.print("Unknown Command. Please select 1-6 based on the menu.");
    }


}
