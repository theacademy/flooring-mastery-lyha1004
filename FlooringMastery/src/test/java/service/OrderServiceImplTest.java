package service;

import com.sg.flooringmastery.dao.*;
import dao.OrderDao;
import dao.ProductDao;
import dao.TaxDao;
import dto.Order;
import dto.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {
    private OrderServiceImpl orderService;



    public OrderServiceImplTest() {
        ApplicationContext ctx =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        orderService =
                (OrderServiceImpl) ctx.getBean("serviceLayer", OrderService.class);
    }

    @BeforeEach
    void setUp() {
        
    }

    private Order createSampleOrder() {
        Order order = new Order();
        order.setOrderNumber(1);
        order.setOrderDate(LocalDate.now());
        order.setCustomerName("John Doe");
        order.setState("TX");
        order.setTaxRate(new BigDecimal("6.25"));
        order.setProductType("Wood");
        order.setArea(new BigDecimal("150"));
        return order;
    }

    @Test
    void testGenerateOrderNumber() {
        int orderNumber1 = orderService.generateOrderNumber();
        int orderNumber2 = orderService.generateOrderNumber();

        assertEquals(1, orderNumber1);
        assertEquals(2, orderNumber2);
    }

    @Test
    void testGetOrders() {
        LocalDate date = LocalDate.now();
        List<Order> orders = orderService.getOrders(date);

        assertNotNull(orders);
        assertEquals(0, orders.size());
    }

    @Test
    void testAddAndRetrieveOrder() {
        LocalDate date = LocalDate.now();
        Order order = createSampleOrder();
        order.setOrderNumber(1);

        orderService.addOrder(order);
        Order retrievedOrder = orderService.getOrder(1, date);

        assertNotNull(retrievedOrder);
        assertEquals("John Doe", retrievedOrder.getCustomerName());
    }

    @Test
    void testRemoveOrder() {
        LocalDate date = LocalDate.now();
        Order order = createSampleOrder();
        order.setOrderNumber(1);

        orderService.addOrder(order);
        orderService.removeOrder(1, date);

        Order retrievedOrder = orderService.getOrder(1, date);
        assertNull(retrievedOrder);
    }

    @Test
    void testEditOrderSuccessfully() {

        LocalDate date = LocalDate.now();
        Order order = createSampleOrder();
        order.setOrderNumber(1);
        orderService.addOrder(order);


        Order updatedOrder = createSampleOrder();
        updatedOrder.setOrderNumber(1);
        updatedOrder.setCustomerName("Jane Doe");
        orderService.editOrder(updatedOrder);


        Order retrievedOrder = orderService.getOrder(1, date);
        assertNotNull(retrievedOrder);
        assertEquals("Jane Doe", retrievedOrder.getCustomerName());
    }

    @Test
    void testEditOrderWhenNotExists() {

        LocalDate date = LocalDate.now();
        Order nonExistentOrder = createSampleOrder();
        nonExistentOrder.setOrderNumber(99);


        Exception exception = assertThrows(OrderNotFoundException.class, () -> {
            orderService.editOrder(nonExistentOrder);
        });
        assertEquals("Order with order number 99 not found.", exception.getMessage());
    }

    @Test
    void testEditOrderWithMultipleOrdersOnSameDate() {

        LocalDate date = LocalDate.now();
        Order order1 = createSampleOrder();
        order1.setOrderNumber(1);
        orderService.addOrder(order1);

        Order order2 = createSampleOrder();
        order2.setOrderNumber(2);
        order2.setCustomerName("Alice");
        orderService.addOrder(order2);


        Order updatedOrder1 = createSampleOrder();
        updatedOrder1.setOrderNumber(1);
        updatedOrder1.setCustomerName("Jane Doe");
        orderService.editOrder(updatedOrder1);


        Order retrievedOrder1 = orderService.getOrder(1, date);
        assertEquals("Jane Doe", retrievedOrder1.getCustomerName());

        Order retrievedOrder2 = orderService.getOrder(2, date);
        assertEquals("Alice", retrievedOrder2.getCustomerName());
    }


    @Test
    void testPreviewOrder() {
        Order order = createSampleOrder();
        Order previewedOrder = orderService.previewOrder(order);

        assertNotNull(previewedOrder);
        assertEquals(order.getCustomerName(), previewedOrder.getCustomerName());
    }


    @Test
    void testExportData() {
        LocalDate date = LocalDate.now();
        assertDoesNotThrow(() -> orderService.exportData(date));
    }

    @Test
    void testExportAllData() {
        assertDoesNotThrow(() -> orderService.exportAllData());
    }

    @Test
    void testGetProductByType_InvalidTypeThrowsException() {
        DataValidationException exception = assertThrows(DataValidationException.class, () -> {
            orderService.getProductByType("InvalidType");
        });

        assertEquals("Product type not found: InvalidType", exception.getMessage());
    }

    @Test
    void testCalculateMaterialCost() {
        Order order = createSampleOrder();
        BigDecimal materialCost = orderService.calculateMaterialCost(order);

        assertEquals(new BigDecimal("750.00"), materialCost);
    }

    @Test
    void testCalculateLaborCost() {
        Order order = createSampleOrder();
        BigDecimal laborCost = orderService.calculateLaborCost(order);

        assertEquals(new BigDecimal("600.00"), laborCost);
    }

    @Test
    void testCalculateTax() {
        Order order = createSampleOrder();
        BigDecimal taxAmount = orderService.calculateTax(order);

        assertEquals(new BigDecimal("84.38"), taxAmount);
    }

    @Test
    void testCalculateTotal() {
        Order order = createSampleOrder();
        BigDecimal totalCost = orderService.calculateTotal(order);

        assertEquals(new BigDecimal("1434.38"), totalCost);
    }

    @Test
    void testValidateDateWithPastDate() {
        String pastDate = "01-01-2020";
        DataValidationException exception = assertThrows(DataValidationException.class, () -> {
            orderService.validateDate(pastDate);
        });
        assertEquals("Order Date must be in the future.", exception.getMessage());
    }

    @Test
    void testValidateDateWithInvalidFormat() {
        String invalidDate = "2020-01-01";
        DataValidationException exception = assertThrows(DataValidationException.class, () -> {
            orderService.validateDate(invalidDate);
        });
        assertEquals("Invalid input. Please enter a valid date in MM-dd-yyyy format.", exception.getMessage());
    }

    @Test
    void testValidateDateWithValidFutureDate() {
        String futureDate = "12-31-2025";
        LocalDate result = orderService.validateDate(futureDate);
        assertEquals(LocalDate.of(2025, 12, 31), result);
    }


    @Test
    void testValidateNameWithInvalidCharacters() {
        String invalidName = "John@Doe";
        DataValidationException exception = assertThrows(DataValidationException.class, () -> {
            orderService.validateName(invalidName);
        });
        assertEquals("Invalid Customer Name.", exception.getMessage());
    }

    @Test
    void testValidateNameWithValidName() {
        String validName = "John Doe";
        String result = orderService.validateName(validName);
        assertEquals(validName, result);
    }

    @Test
    void testValidateStateWithInvalidState() {
        String invalidState = "XX";
        DataValidationException exception = assertThrows(DataValidationException.class, () -> {
            orderService.validateState(invalidState);
        });
        assertEquals("Sorry, we cannot sell to this state.", exception.getMessage());
    }

    @Test
    void testValidateStateWithValidState() {
        String validState = "TX";
        String result = orderService.validateState(validState);
        assertEquals(validState, result);
    }

    @Test
    void testNullStateThrowsException() {
        Exception exception = assertThrows(DataValidationException.class, () -> {
            orderService.getStateTax(null);
        });

        assertEquals("State abbreviation cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testEmptyStateThrowsException() {
        Exception exception = assertThrows(DataValidationException.class, () -> {
            orderService.getStateTax("");
        });

        assertEquals("State abbreviation cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testInvalidStateThrowsException() {
        Exception exception = assertThrows(DataValidationException.class, () -> {
            orderService.getStateTax("XX");
        });

        assertEquals("State abbreviation not found: XX", exception.getMessage());
    }


    @Test
    void testValidateProductTypeWithInvalidType() {
        String invalidType = "InvalidProduct";
        DataValidationException exception = assertThrows(DataValidationException.class, () -> {
            orderService.validateProductType(invalidType);
        });
        assertEquals("We do not have this product available. Please look at our list and enter an available product.", exception.getMessage());
    }

    @Test
    void testValidateProductTypeWithValidType() {
        String validType = "Wood";
        String result = orderService.validateProductType(validType);
        assertEquals(validType, result);
    }

    @Test
    void testInvalidProductTypeException() {
        String invalidType = "NonExistentProduct";
        Exception exception = assertThrows(DataValidationException.class, () -> {
            orderService.validateProductType(invalidType);
        });

        assertEquals("We do not have this product available. Please look at our list and enter an available product.", exception.getMessage());
    }

    @Test
    void testValidateAreaWithInvalidArea() {
        String invalidArea = "50";
        DataValidationException exception = assertThrows(DataValidationException.class, () -> {
            orderService.validateArea(invalidArea);
        });
        assertEquals("Area must be at least 100 sq ft.", exception.getMessage());
    }

    @Test
    void testValidateArea_NullAreaThrowsException() {
        DataValidationException exception = assertThrows(DataValidationException.class, () -> {
            orderService.validateArea("");
        });

        assertEquals("Invalid area. Please enter a valid number.", exception.getMessage());
    }

    @Test
    void testValidateAreaWithValidArea() {
        String validArea = "150";
        BigDecimal comparison = new BigDecimal("150");
        BigDecimal result = orderService.validateArea(validArea);
        assertEquals(comparison, result);
    }

    @Test
    void testInvalidAreaException() {
        String invalidArea = "50";
        Exception exception = assertThrows(DataValidationException.class, () -> {
            orderService.validateArea(invalidArea);
        });

        assertEquals("Area must be at least 100 sq ft.", exception.getMessage());
    }

    @Test
    void testInvalidAreaInputException() {
        String invalidArea = "abc";
        Exception exception = assertThrows(DataValidationException.class, () -> {
            orderService.validateArea(invalidArea);
        });

        assertEquals("Invalid area. Please enter a valid number.", exception.getMessage());
    }

    @Test
    void testCalculateOrderCost() {
        Order order = createSampleOrder();

        Order calculatedOrder = orderService.calculateOrderCost(order);

        assertNotNull(calculatedOrder);
        assertEquals(new BigDecimal("750.00"), calculatedOrder.getMaterialCost());
        assertEquals(new BigDecimal("600.00"), calculatedOrder.getLaborCost());
        assertEquals(new BigDecimal("84.38"), calculatedOrder.getTax());
        assertEquals(new BigDecimal("1434.38"), calculatedOrder.getTotal());
    }

    @Test
    void testCalculateOrderCost_ExceptionHandling() {
        Order invalidOrder = new Order();

        DataValidationException exception = assertThrows(DataValidationException.class, () -> {
            orderService.calculateOrderCost(invalidOrder);
        });

        assertEquals("Error calculating order cost", exception.getMessage());
    }
}
