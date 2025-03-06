package service;

import com.sg.flooringmastery.dao.*;
import dao.OrderDao;
import dao.ProductDao;
import dao.TaxDao;
import dto.Order;
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
        OrderDao orderDaoStub = new OrderDaoStubImpl();
        ProductDao productDaoStub = new ProductDaoStubImpl();
        TaxDao taxDaoStub = new TaxDaoStubImpl();

        orderService = new OrderServiceImpl(orderDaoStub, productDaoStub, taxDaoStub);
    }

    private Order createSampleOrder() {
        Order order = new Order();
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
}
