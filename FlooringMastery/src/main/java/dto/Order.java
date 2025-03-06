package dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Order {
    private int orderNumber;
    private LocalDate orderDate;
    private String customerName;
    private String state;
    private String productType;
    private BigDecimal area;
    private BigDecimal taxRate;
    private BigDecimal costPerSquareFoot;
    private BigDecimal laborCostPerSquareFoot;
    private BigDecimal materialCost;
    private BigDecimal laborCost;
    private BigDecimal tax;
    private BigDecimal total;



    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getProductType() {
        return productType;

    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public void setCostPerSquareFoot(String costPerSquareFoot) {
        this.costPerSquareFoot = new BigDecimal(costPerSquareFoot);
    }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public void setLaborCostPerSquareFoot(String laborCostPerSquareFoot) {
        this.laborCostPerSquareFoot = new BigDecimal(laborCostPerSquareFoot);
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public void setLaborCost(BigDecimal laborCost) {
        this.laborCost = laborCost;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void updateFields(Order updatedOrder) {
        if (!updatedOrder.getCustomerName().isEmpty()) {
            this.customerName = updatedOrder.getCustomerName();
        }
        if (!updatedOrder.getState().isEmpty()) {
            this.state = updatedOrder.getState();
        }
        if (!updatedOrder.getProductType().isEmpty()) {
            this.productType = updatedOrder.getProductType();
        }
        if (updatedOrder.getArea() != null) {
            this.area = updatedOrder.getArea();
        }
    }

    @Override
    public String toString() {
        return orderNumber +  "," +
                customerName + "," +
                state + "," +
                taxRate +  "," +
                productType + "," +
                area + "," +
                costPerSquareFoot +  "," +
                laborCostPerSquareFoot +  "," +
                materialCost + "," +
                laborCost + "," +
                tax + "," +
                total;
    }

    public String toStringDate() {
        return orderDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
    }

}