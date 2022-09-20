package ca.lichangzhang.flooringmastery.dao;

import ca.lichangzhang.flooringmastery.dto.Order;
import ca.lichangzhang.flooringmastery.dto.Product;
import ca.lichangzhang.flooringmastery.dto.Tax;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/*
*
* @author catzh
* Name: Li Chang Zhang
* Email: xiaoyuzhang668@gmail.com
* Date: 2022
* 
 */
public class FlooringMasteryDaoFileImplTest {

    FlooringMasteryDao testDao;

    public FlooringMasteryDaoFileImplTest() {
    }

    @BeforeAll
    public static void setUpClass()  {         
    }

    @AfterAll
    public static void tearDownClass() {
        try {
            Files.deleteIfExists(
                    Paths.get("TestOrders/Orders_06022033.txt"));
        } catch (NoSuchFileException e) {
            System.out.println(
                    "No such file/directory exists");
        } catch (DirectoryNotEmptyException e) {
            System.out.println("Directory is not empty.");
        } catch (IOException e) {
            System.out.println("Invalid permissions.");
        }
        System.out.println("After all tear down.");
    }

    @BeforeEach
    public void setUp() throws Exception {

        String orderFilePath = "TestOrders";
        String backupFilePath = "TestBackup/DataExport.txt";

        new FileWriter(orderFilePath+"/Orders_06022033.txt"); //blank the file

        testDao = new FlooringMasteryDaoFileImpl(orderFilePath, backupFilePath);

        FileUtils.writeStringToFile(new File(orderFilePath + "/Orders_06022033.txt"),
                "OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total\n",
                Charset.forName("UTF-8"));
    }

    @AfterEach
    public void tearDown() {
    }

        //=================1.  GENERATE AUTO ORDER NUMBER  ========================================= 
    @Test
    public void testGetAutoOrderNumber() throws Exception {
        //        create two orders
        Product product = new Product("Iron", new BigDecimal(3.01), new BigDecimal(2.01));

        Tax tax = new Tax("IN", "Indiana", new BigDecimal(1.01));

        String fileName = "Orders_06022033.txt";
        Order order = new Order(88);
        order.setCustomerName("Amy Cheung");
        order.setTax(tax);
        order.setProduct(product);
        order.setArea(new BigDecimal(100.02));
        order.setMaterialCost(new BigDecimal(301.06));
        order.setLaborCost(new BigDecimal(201.04));
        order.setTaxSub(new BigDecimal(5.07));
        order.setTotal(new BigDecimal(507.17));
        //ARRANGE
        //Add our order to the DAO
        testDao.addOrder(fileName, order);

        int autoNumber = testDao.getAutoOrderNumber();
        //ACT
        assertEquals(autoNumber, order.getOrderNumber() + 1,
                "The next auto generated order number should be 89.");
    }

        //=================2.  ADD AND GET ORDER  ========================================= 
    @Test
    public void testAddGetOrder() throws Exception {

        //        create one orders
        Product productOne = new Product("Iron", new BigDecimal(3.01), new BigDecimal(2.01));

        Tax taxOne = new Tax("IN", "Indiana", new BigDecimal(1.01));

        String fileNameOne = "Orders_06022033.txt";
        Order order = new Order(88);
        order.setCustomerName("Amy Cheung");
        order.setTax(taxOne);
        order.setProduct(productOne);
        order.setArea(new BigDecimal(100.02));
        order.setMaterialCost(new BigDecimal(301.06));
        order.setLaborCost(new BigDecimal(201.04));
        order.setTaxSub(new BigDecimal(5.07));
        order.setTotal(new BigDecimal(507.17));

        //Add our order to the DAO
        testDao.addOrder(fileNameOne, order);

        // Retrieve the order within the DAO
        Order retrievedOrder = testDao.openFile(fileNameOne).get(order.getOrderNumber());

        // Check the data is equal
        assertEquals(order.getOrderNumber(),
                retrievedOrder.getOrderNumber(),
                "Checking order number.");
        assertEquals(order.getCustomerName(),
                retrievedOrder.getCustomerName(),
                "Checking order customer name.");
        assertEquals(order.getTax(),
                retrievedOrder.getTax(),
                "Checking tax information.");
        assertEquals(order.getProduct(),
                retrievedOrder.getProduct(),
                "Checking product information.");
        assertEquals(order.getArea(),
                retrievedOrder.getArea(),
                "Checking order area.");
        assertEquals(order.getMaterialCost(),
                retrievedOrder.getMaterialCost(),
                "Checking material cost.");
        assertEquals(order.getLaborCost(),
                retrievedOrder.getLaborCost(),
                "Checking labor cost.");
        assertEquals(order.getTaxSub(),
                retrievedOrder.getTaxSub(),
                "Checking orer tax.");
        assertEquals(order.getTotal(),
                retrievedOrder.getTotal(),
                "Checking order total.");
    }

    @Test
    public void testAddGetAllOrders() throws Exception {

        //        create two orders
        Product productOne = new Product("Iron", new BigDecimal(3.01), new BigDecimal(2.01));
        Product productTwo = new Product("Steel", new BigDecimal(4.00), new BigDecimal(3.00));

        Tax taxOne = new Tax("IN", "Indiana", new BigDecimal(1.01));
        Tax taxTwo = new Tax("OR", "Oregon", new BigDecimal(1.02));

        String fileName = "Orders_06022033.txt";
        Order orderOne = new Order(88);
        orderOne.setCustomerName("Amy Cheung");
        orderOne.setTax(taxOne);
        orderOne.setProduct(productOne);
        orderOne.setArea(new BigDecimal(100.02));
        orderOne.setMaterialCost(new BigDecimal(301.06));
        orderOne.setLaborCost(new BigDecimal(201.04));
        orderOne.setTaxSub(new BigDecimal(5.07));
        orderOne.setTotal(new BigDecimal(507.17));
    

        // Add both our orders to the DAO
        testDao.addOrder(fileName, orderOne);  

        // Retrieve the list of all orders within the DAO
        List<Order> allOrders = new ArrayList<>(testDao.openFile(fileName).values());

        // First check the general contents of the list
        assertNotNull(allOrders, "The list of orders must not null");
        assertEquals(1, allOrders.size(), "List of orders should have 1 orders.");

        // Then the specifics
        assertTrue(testDao.openFile(fileName).values().contains(orderOne),
                "The list of orders should include Amy Zhang.");
    
    }

        //=================3.  EDIT ORDER  ========================================= 
    @Test
    public void testEditGetAllOrders() throws Exception {
        //        create two orders
        Product product = new Product("Iron", new BigDecimal(3.01), new BigDecimal(2.01));

        Tax tax = new Tax("IN", "Indiana", new BigDecimal(1.01));

        String fileName = "Orders_06022033.txt";
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

        // Add order to the DAO
        testDao.addOrder(fileName, order);

        order.setCustomerName("Nancy Chen");

        testDao.editOrder(fileName, order);

        // Retrieve the list of all orders within the DAO
        List<Order> allOrders = new ArrayList<>(testDao.openFile(fileName).values());

        // First check the general contents of the list
        assertNotNull(allOrders, "The list of orders must not null");
        assertEquals(1, allOrders.size(), "List of orders should have 1 orders.");

        // Then the specifics
        assertEquals(allOrders.get(0), order,
                "The changed order should be the same as the only order in the order file.");
    }
    
    //=================4.  REMOVE ORDER  ========================================= 
    @Test
    public void testRemoveOrders() throws Exception {       
        // create two orders
        Product productOne = new Product("Iron", new BigDecimal(3.01), new BigDecimal(2.01));

        Tax taxOne = new Tax("IN", "Indiana", new BigDecimal(1.01));      

        String fileName = "Orders_06022033.txt";
        Order orderOne = new Order(88);
        orderOne.setCustomerName("Nancy Chen");
        orderOne.setTax(taxOne);
        orderOne.setProduct(productOne);
        orderOne.setArea(new BigDecimal(100.02));
        orderOne.setMaterialCost(new BigDecimal(301.06));
        orderOne.setLaborCost(new BigDecimal(201.04));
        orderOne.setTaxSub(new BigDecimal(5.07));
        orderOne.setTotal(new BigDecimal(507.17));

//         Add both our orders to the DAO
        testDao.addOrder(fileName, orderOne);

        //         Retrieve the list of all orders within the DAO
        Map<Integer,Order> allOrders = testDao.openFile(fileName);
        assertTrue(allOrders.containsKey(orderOne.getOrderNumber()), "All students should include Amy Zhang.");

//         remove the first order - Amy Zhang
        Order removedOrder = testDao.removeOrder(fileName, orderOne.getOrderNumber());

//         Check that the correct object was removed.
        assertEquals(removedOrder, orderOne, "The removed order should be Amy Cheung.");
//   
//         Check the contents of the list - it should be empty
        assertTrue(allOrders.isEmpty(), "The retrieved list of orders should be empty.");
    }

        //=================6.  EXPORT ORDER  ========================================= 
    @Test
    public void testExportData() throws Exception {     
        //        create order
        Product product = new Product("Iron", new BigDecimal(3.01), new BigDecimal(2.01));
        Tax tax = new Tax("IN", "Indiana", new BigDecimal(1.01));
        String fileName = "Orders_06022033.txt";
        Order order = new Order(88);
        order.setCustomerName("Amy Cheung");
        order.setTax(tax);
        order.setProduct(product);
        order.setArea(new BigDecimal(100.02));
        order.setMaterialCost(new BigDecimal(301.06));
        order.setLaborCost(new BigDecimal(201.04));
        order.setTaxSub(new BigDecimal(5.07));
        order.setTotal(new BigDecimal(507.17));

        //Add our order to the DAO
        testDao.addOrder(fileName, order);

        testDao.exportData();

        // Retrieve the list of all orders within from txt file
        // List<String> allList = Files.readAllLines(new File("TestBackup/DataExport.txt").toPath(), Charset.defaultCharset());
        List<String> exports = new BufferedReader(new FileReader("TestBackup/DataExport.txt"))
                .lines()
                .skip(1) //Skips the first n lines, in this case 1      
                .map(export -> {
                    String[] arr = Pattern.compile("\n").split(export);
                    return export;
                }).collect(Collectors.toList());

        // First check the general contents of the list
        assertNotNull(exports, "The list of data export must not null");
        assertEquals(4, exports.size(), "Data export file should have 4 orders.");
    }
}
