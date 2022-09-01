package ca.lichangzhang.flooringmastery.service;

import ca.lichangzhang.flooringmastery.dao.FlooringMasteryPersistenceException;
import ca.lichangzhang.flooringmastery.dto.Order;
import ca.lichangzhang.flooringmastery.dto.Product;
import ca.lichangzhang.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/*
*
* @author catzh
* Name: Li Chang Zhang
* Email: xiaoyuzhang668@gmail.com
* Date: 2022
* 
 */
public class FlooringMasteryServiceImplTest {

    private FlooringMasteryService service;

    public FlooringMasteryServiceImplTest() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("applicationContext.xml");
        service
                = ctx.getBean("service", FlooringMasteryService.class);

    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    //================== 1. ADD ORDER ======================
    @Test
    public void testCreateValidOrder() {
        // ARRANGE    
        String fileName = "02022038";
        Product product = new Product("Steel", new BigDecimal(4.00), new BigDecimal(3.00));
        Tax tax = new Tax("OR", "Oregon", new BigDecimal(1.02));
        Order order = new Order(138);
        order.setCustomerName("Sandy Wong");
        order.setTax(tax);
        order.setProduct(product);
        order.setArea(new BigDecimal(100.01));
        order.setMaterialCost(new BigDecimal(400.04));
        order.setLaborCost(new BigDecimal(300.03));
        order.setTaxSub(new BigDecimal(7.14));
        order.setTotal(new BigDecimal(1414.14));
        // ACT
        try {
            service.addOrder(fileName, order);
        } catch (FlooringMasteryFutureDateException
                | FlooringMasteryPersistenceException e) {
            // ASSERT
            fail("Order was valid. No exception should have been thrown.");
        }
    }

    //order date does not exist
    @Test
    public void testValidateOrderFile() {
        // ARRANGE
        String fileName = ("02022038");
        Product product = new Product("Iron", new BigDecimal(3.01), new BigDecimal(2.01));
        Tax tax = new Tax("IN", "Indiana", new BigDecimal(1.01));
        int orderNumber = 88;
        Order order = new Order(orderNumber);
        order.setCustomerName("Amy Cheung");
        order.setTax(tax);
        order.setProduct(product);
        order.setArea(new BigDecimal(100.02));
        order.setMaterialCost(new BigDecimal(301.06));
        order.setLaborCost(new BigDecimal(201.04));
        order.setTaxSub(new BigDecimal(5.07));
        order.setTotal(new BigDecimal(507.17));
        // ACT
        try {
            service.editOrder(fileName, order);
            service.removeOrder(fileName, orderNumber);
            fail("Expected No Orer File Exception was not thrown.");
        } catch (FlooringMasteryNoOrderException
                | FlooringMasteryPersistenceException e) {
            // ASSERT
            fail("Incorrect exception was thrown.");
        } catch (FlooringMasteryNoOrderFileException e) {
            return;
        }
    }

    //order number does not exist
    @Test
    public void testValidateOrder() {
        // ARRANGE
        String fileName = ("02022033");
        Product product = new Product("Iron", new BigDecimal(3.01), new BigDecimal(2.01));
        Tax tax = new Tax("IN", "Indiana", new BigDecimal(1.01));
        int orderNumber = 138;
        Order order = new Order(orderNumber);
        order.setCustomerName("Amy Cheung");
        order.setTax(tax);
        order.setProduct(product);
        order.setArea(new BigDecimal(100.02));
        order.setMaterialCost(new BigDecimal(301.06));
        order.setLaborCost(new BigDecimal(201.04));
        order.setTaxSub(new BigDecimal(5.07));
        order.setTotal(new BigDecimal(507.17));
        // ACT
        try {
            service.editOrder(fileName, order);
            service.removeOrder(fileName, orderNumber);
            fail("Expected No Orer Number Exception was not thrown.");
        } catch (FlooringMasteryNoOrderFileException
                | FlooringMasteryPersistenceException e) {
            // ASSERT
            fail("Incorrect exception was thrown.");
        } catch (FlooringMasteryNoOrderException e) {
            return;
        }
    }

    // order date is not in the future
    @Test
    public void testValidateFutureDate() {
        // ARRANGE
        String fileName = ("02022000");
        Product product = new Product("Iron", new BigDecimal(3.01), new BigDecimal(2.01));
        Tax tax = new Tax("IN", "Indiana", new BigDecimal(1.01));
        int orderNumber = 88;
        Order order = new Order(orderNumber);
        order.setCustomerName("Amy Cheung");
        order.setTax(tax);
        order.setProduct(product);
        order.setArea(new BigDecimal(100.02));
        order.setMaterialCost(new BigDecimal(301.06));
        order.setLaborCost(new BigDecimal(201.04));
        order.setTaxSub(new BigDecimal(5.07));
        order.setTotal(new BigDecimal(507.17));
        // ACT
        try {
            service.addOrder(fileName, order);
            fail("Expected Not Future Date Exception was not thrown.");
        } catch (FlooringMasteryPersistenceException e) {
            // ASSERT
            fail("Incorrect exception was thrown.");
        } catch (FlooringMasteryFutureDateException e) {
            return;
        }
    }

    //================== 2. GET ALL ORDER ======================
    @Test
    public void testGetAllOrder() throws Exception {
        // ARRANGE       
        //        create one orders
        Product productOne = new Product("Iron", new BigDecimal(3.01), new BigDecimal(2.01));

        Tax taxOne = new Tax("IN", "Indiana", new BigDecimal(1.01));

        String fileName = "02022033";
        Order order = new Order(88);
        order.setCustomerName("Amy Cheung");
        order.setTax(taxOne);
        order.setProduct(productOne);
        order.setArea(new BigDecimal(100.02));
        order.setMaterialCost(new BigDecimal(301.06));
        order.setLaborCost(new BigDecimal(201.04));
        order.setTaxSub(new BigDecimal(5.07));
        order.setTotal(new BigDecimal(507.17));

        // ACT & ASSERT
        // Check the data is equal
        assertEquals(1, service.openFile(fileName).size(),
                "Should only have one order.");
        assertTrue(service.openFile(fileName).containsKey(order.getOrderNumber()),
                "The one order should be order number 88.");
    }

    //==================  3. CALCULATE ORDER ======================
    @Test
    public void testCalculateTotal() throws Exception {
        // ARRANGE
        Product productOne = new Product("Iron", new BigDecimal(3.01), new BigDecimal(2.01));

        Tax taxOne = new Tax("IN", "Indiana", new BigDecimal(1.01));

        String fileName = "02022033";
        Order order = new Order(88);
        order.setCustomerName("Amy Cheung");
        order.setTax(taxOne);
        order.setProduct(productOne);
        order.setArea(new BigDecimal(100.02));
        order.setMaterialCost(new BigDecimal(301.06));
        order.setLaborCost(new BigDecimal(201.04));
        order.setTaxSub(new BigDecimal(5.07));
        order.setTotal(new BigDecimal(507.17));
        //ACT
        Order calculatedTotal = service.calculateTotal(order, service.loadTax(), service.loadProduct());

        assertEquals(calculatedTotal, order,
                "Two orders should be the same.");
    }

    //================== 4. REMOVE ORDER ======================
    @Test
    public void testRemoveOrder() throws Exception {
        // ARRANGE
        //        create one orders
        Product productOne = new Product("Iron", new BigDecimal(3.01), new BigDecimal(2.01));

        Tax taxOne = new Tax("IN", "Indiana", new BigDecimal(1.01));

        String fileName = "02022033";
        Order order = new Order(88);
        order.setCustomerName("Amy Cheung");
        order.setTax(taxOne);
        order.setProduct(productOne);
        order.setArea(new BigDecimal(100.02));
        order.setMaterialCost(new BigDecimal(301.06));
        order.setLaborCost(new BigDecimal(201.04));
        order.setTaxSub(new BigDecimal(5.07));
        order.setTotal(new BigDecimal(507.17));
        service.calculateTotal(order, service.loadTax(), service.loadProduct());

        // ACT & ASSERT
        Order shouldBeOne = service.removeOrder(fileName, 88);
        assertNotNull(shouldBeOne, "Removing order number 88 should be not null.");
        assertEquals(order, shouldBeOne, "Order removed from order number 88 should be Amy Cheung.");
    }

    //================== 5. EDIT ORDER ======================
    @Test
    public void testEditOrder() throws Exception {
        // ARRANGE
        //        create one orders
        Product productOne = new Product("Iron", new BigDecimal(3.01), new BigDecimal(2.01));

        Tax taxOne = new Tax("IN", "Indiana", new BigDecimal(1.01));

        String fileName = "02022033";
        Order order = new Order(88);
        order.setCustomerName("Amy Cheung");
        order.setTax(taxOne);
        order.setProduct(productOne);
        order.setArea(new BigDecimal(100.02));
        order.setMaterialCost(new BigDecimal(301.06));
        order.setLaborCost(new BigDecimal(201.04));
        order.setTaxSub(new BigDecimal(5.07));
        order.setTotal(new BigDecimal(507.17));

        // ARRANGE    - CHANGE PRODUCT TYPE, TAX NAME, CUSTOMER NAME, AND AREA
        Product productChanged = new Product("Steel", new BigDecimal(4.00), new BigDecimal(3.00));
        Tax taxChanged = new Tax("OR", "Oregon", new BigDecimal(1.02));
        Order orderChanged = new Order(88);
        orderChanged.setCustomerName("Sandy Wong");
        orderChanged.setTax(taxChanged);
        orderChanged.setProduct(productChanged);
        orderChanged.setArea(new BigDecimal(100.01));

        // ACT - CALCULATE BASED ON NEW VALUE
        Order edittedOrder = service.editOrder(fileName, orderChanged);

        // get the new changed order
        service.calculateTotal(orderChanged, service.loadTax(), service.loadProduct());

        //after change, sum up the value for compared base order
        orderChanged.setMaterialCost(new BigDecimal(400.04));
        orderChanged.setLaborCost(new BigDecimal(300.03));
        orderChanged.setTaxSub(new BigDecimal(7.14));
        orderChanged.setTotal(new BigDecimal(1414.14));

        // ACT & ASSERT - retrieved and changed order should be same
        // First check the general contents of the list
        assertNotNull(service.openFile(fileName), "The list of order must not null");
        assertEquals(1, service.openFile(fileName).size(), "List of order should have 1 order.");

        // Then the specifics
        assertTrue(service.openFile(fileName).containsKey(orderChanged.getOrderNumber()),
                "The list of order should include order number 88.");
        assertEquals(orderChanged, edittedOrder,
                "Two orders from daoStub and changed order should be the same.");

    }
}
