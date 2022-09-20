package ca.lichangzhang.vendingmachine.dao;

import ca.lichangzhang.vendingmachine.dto.Item;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
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
public class VendingMachineDaoFileImplTest {

    VendingMachineDao testDao;

    public VendingMachineDaoFileImplTest() {

    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() throws Exception {

        String testFile = "testfile.txt";
        // Use the FileWriter to quickly blank the file
        new FileWriter(testFile);
        
        PrintWriter out;
        out = new PrintWriter(new FileWriter(testFile));
        
        testDao = new VendingMachineDaoFileImpl(testFile);
       
        // Create our first Item ADD
        String firstItem = "1::Glitter Potion::2.75::5";       

        // Create our second item
        String secondItem = "2::Elf Bracelet::9.75::8";   
 
         // write the item object to the file
        out.println(firstItem);
        out.println(secondItem);
         
       // force PrintWriter to write line to the file
        out.flush();
         
        //closing the writer
        out.close();   
    }

    @AfterEach
    public void tearDown() {
    }

    //GET ALL
    @Test
    public void testGetAll() throws Exception {

        // Create our first Item ADD
        Item firstItem = new Item(1);
        firstItem.setItemName("Glitter Potion");
        firstItem.setItemCost(BigDecimal.valueOf(2.75));
        firstItem.setInventoryNum(5);

        // Create our second item
        Item secondItem = new Item(2);
        secondItem.setItemName("Elf Bracelet");
        secondItem.setItemCost(BigDecimal.valueOf(9.75));
        secondItem.setInventoryNum(8);

        // Retrieve the list of all items within the DAO  ACT
        List<Item> itemsFromDao = testDao.getAll();
        // ASSERT - First check the general contents of the list should be one entry because Elf Bracele's inventory is not 0
        assertNotNull(itemsFromDao, "The list of items must not null");
        assertEquals(2, itemsFromDao.size(), "List of item should have 2 items.");

        // Then the specifics
        assertTrue(testDao.getAll().contains(firstItem),
                "The list of items should include 1 - Glitter Potion .");
        assertTrue(testDao.getAll().contains(secondItem),
                "The list of items should include 2 - Elf Bracelet.");
    }

    //FIND 
    @Test
    public void testFindItem() throws Exception {
        // ADD - Create our method test inputs
        Item firstItem = new Item(1);
        firstItem.setItemName("Glitter Potion");
        firstItem.setItemCost(BigDecimal.valueOf(2.75));
        firstItem.setInventoryNum(5);

        // ACT Get the item from the DAO
        Item retrievedItem = testDao.findItem(1);

        //ASSERT - Check the data is equal
        assertEquals(firstItem.getItemNum(),
                retrievedItem.getItemNum(),
                "Checking item number.");
        assertEquals(firstItem.getItemName(),
                retrievedItem.getItemName(),
                "Checking item name.");
        assertEquals(firstItem.getItemCost(),
                retrievedItem.getItemCost(),
                "Checking item cost.");
        assertEquals(firstItem.getInventoryNum(),
                retrievedItem.getInventoryNum(),
                "Checking item inventory number.");
    }

    //PURCHASE ITEM
    @Test
    public void testPurchaseItem() throws Exception {
        //ADD - get the cost and inventory number of first item from DAO
        // Create our first Item ADD
        Item firstItem = new Item(1);
        firstItem.setItemName("Glitter Potion");
        firstItem.setItemCost(BigDecimal.valueOf(2.75));
        firstItem.setInventoryNum(5);

        Item oldItem = testDao.findItem(1);
        int oldInventoryNum = oldItem.getInventoryNum();

        //ACT
        BigDecimal originalBalance = BigDecimal.valueOf(38);
        BigDecimal balance = testDao.purchaseItem(originalBalance, 1);
        
        //get new inventory number from DAO
        Item newItem = testDao.findItem(1);
        int newInventoryNum = newItem.getInventoryNum();

        //ASSERT - new inventory number will be old inventory number minus 1
        assertEquals(newInventoryNum, (oldInventoryNum - 1), "New inventory number should be original inventory number minus 1.");
        assertEquals(originalBalance.subtract(firstItem.getItemCost()), balance, "Calculated balance should be matching.");
    }
}
