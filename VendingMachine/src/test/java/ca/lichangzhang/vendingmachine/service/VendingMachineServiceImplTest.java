package ca.lichangzhang.vendingmachine.service;

import ca.lichangzhang.vendingmachine.dao.VendingMachineAuditDao;
import ca.lichangzhang.vendingmachine.dao.VendingMachineDao;
import ca.lichangzhang.vendingmachine.dao.VendingMachinePersistenceException;
import ca.lichangzhang.vendingmachine.dto.Change;
import ca.lichangzhang.vendingmachine.dto.Item;
import java.math.BigDecimal;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
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
//Test the following
//purchase item with insufficient amount of money
//purchase item which has zero inventory
//purchase item with zero deposit
public class VendingMachineServiceImplTest {

    private VendingMachineService service;

    public VendingMachineServiceImplTest() {
        VendingMachineDao dao = new VendingMachineDaoStubImpl();
        VendingMachineAuditDao auditDao = new VendingMachineAuditDaoStubImpl();

        service = new VendingMachineServiceImpl(dao, auditDao);
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

    @Test
    //TEST PURCHASE WITH VALID CONDITION
    public void testValidPurchase() {
        //ARRANGE       
        Item item = new Item(1);
        item.setItemName("Glitter Potion");
        item.setItemCost(BigDecimal.valueOf(2.75));
        item.setInventoryNum(5);

        //ACT
        try {
            //set user account balance has 80 dollars
            BigDecimal balance = BigDecimal.valueOf(80);
            //purchase the item
            service.purchaseItem(balance, item);
        } catch (VendingMachineNoItemInventoryException
                | VendingMachineZeroDepositException
                | VendingMachineInsufficientFundsException
                | VendingMachinePersistenceException e) {
            // ASSERT
            fail("Item purchase was valid. No exception should have been thrown.");
        }
    }

    @Test
    //TEST PURCHASE WITH ZERO DEPOSIT
    public void testZeroDeposit() {
        //ARRANGE
        BigDecimal balance = BigDecimal.valueOf(0);//user has zero account balance
        Item item = new Item(1);
        item.setItemName("Glitter Potion");
        item.setItemCost(BigDecimal.valueOf(2.75));
        item.setInventoryNum(5);

        //ACT
        try {
            service.purchaseItem(balance, item);
            fail("Expected zero deposit exceptin was not thrown.");
        } catch (VendingMachineNoItemInventoryException
                | VendingMachineInsufficientFundsException
                | VendingMachinePersistenceException e) {
            // ASSERT
            fail("Incorrect exception was thrown.");
        } catch (VendingMachineZeroDepositException e) {
            return;
        }
    }

    @Test
    //TEST PURCHASE WITH ZERO INVENTORY
    public void testZeroInventory() {
        //ARRANGE
        BigDecimal balance = BigDecimal.valueOf(80);
        Item item = new Item(1);
        item.setItemName("Glitter Potion");
        item.setItemCost(BigDecimal.valueOf(2.75));
        item.setInventoryNum(0);//with zero inventory

        //ACT
        try {
            service.purchaseItem(balance, item);
            fail("Expected zero inventory exceptin was not thrown.");
        } catch (VendingMachineZeroDepositException
                | VendingMachineInsufficientFundsException
                | VendingMachinePersistenceException e) {
            // ASSERT
            fail("Incorrect exception was thrown.");
        } catch (VendingMachineNoItemInventoryException e) {
            return;
        }
    }

    @Test
    //TEST PURCHASE WITH INSUFFICIENT FUND
    public void testInsufficientFund() {
        //ARRANGE
        BigDecimal balance = BigDecimal.valueOf(2);//with insufficient fund
        Item item = new Item(1);
        item.setItemName("Glitter Potion");
        item.setItemCost(BigDecimal.valueOf(2.75));
        item.setInventoryNum(5);

        //ACT
        try {
            service.purchaseItem(balance, item);
            fail("Expected insufficient fund exceptin was not thrown.");
        } catch (VendingMachineZeroDepositException
                | VendingMachineNoItemInventoryException
                | VendingMachinePersistenceException e) {
            // ASSERT
            fail("Incorrect exception was thrown.");
        } catch (VendingMachineInsufficientFundsException e) {
            return;
        }
    }

    //TEST GET ALL ITEMS
    @Test
    public void testGetAllItems() throws Exception {
        //ARRANGE
        Item testClone = new Item(1);
        testClone.setItemName("Glitter Potion");
        testClone.setItemCost(BigDecimal.valueOf(2.75));
        testClone.setInventoryNum(5);

        //ACT & ASSERT
        assertEquals(1, service.getAll().size(),
                "Should only have one item.");
        assertTrue(service.getAll().contains(testClone),
                "The one item should be Glitter Potion.");
    }

    //TEST GET ITEM
    @Test
    public void testGetItem() throws Exception {
        //ARRANGE
        Item testClone = new Item(1);
        testClone.setItemName("Glitter Potion");
        testClone.setItemCost(BigDecimal.valueOf(2.75));
        testClone.setInventoryNum(5);

        //ACT & ASSERT
        Item shouldBeGlitter = service.findItem(1);
        assertNotNull(shouldBeGlitter, "Getting Glitter Potion should not be null.");
        assertEquals(testClone, shouldBeGlitter,
                "Item Glitter Potion should be priced at 2.75 and have inventory number of 5.");

        Item shouldBeNull = service.findItem(88);
        assertNull(shouldBeNull, "Getting 88 should be null.");
    }

    @Test
    public void testGetChange() {
        //find a balance of 0.67 which is able to be divided into quarter, dime, nickel and penny
        //ARRANGE - combination of each coin at least one
        Change change = service.getChange(BigDecimal.valueOf(0.67));

        //ACT & ASSERT        
        assertEquals(2, change.getNumQuarter(),
                "Change should include 2 quarters.");

        assertEquals(1, change.getNumDime(),
                "Change should include 1 dime.");

        assertEquals(1, change.getNumNickel(),
                "Change should include 1 nickel.");

        assertEquals(2, change.getNumPenny(),
                "Change should include 2 pennies.");
    }

    @Test
    public void testGetChangeZeroQuarter() {
      
        //ARRANGE - combination of zero quarter
        Change change = service.getChange(BigDecimal.valueOf(0.23));

        //ACT & ASSERT        
        assertEquals(0, change.getNumQuarter(),
                "Change should include 0 quarters.");

        assertEquals(2, change.getNumDime(),
                "Change should include 2 dime.");

        assertEquals(0, change.getNumNickel(),
                "Change should include 0 nickel.");

        assertEquals(3, change.getNumPenny(),
                "Change should include 3 pennies.");
    }
    
     @Test
    public void testGetChangeZeroDime() {
       
        //ARRANGE - combination of zero dime
        Change change = service.getChange(BigDecimal.valueOf(0.55));

        //ACT & ASSERT        
        assertEquals(2, change.getNumQuarter(),
                "Change should include 2 quarters.");

        assertEquals(0, change.getNumDime(),
                "Change should include 0 dime.");

        assertEquals(1, change.getNumNickel(),
                "Change should include 1 nickel.");

        assertEquals(0, change.getNumPenny(),
                "Change should include 0 pennies.");
    }
    
        @Test
    public void testGetChangeZeroNickel() {
        
        //ARRANGE - combination of zero nickel
        Change change = service.getChange(BigDecimal.valueOf(1.13));

        //ACT & ASSERT        
        assertEquals(4, change.getNumQuarter(),
                "Change should include 4 quarters.");

        assertEquals(1, change.getNumDime(),
                "Change should include 1 dime.");

        assertEquals(0, change.getNumNickel(),
                "Change should include 0 nickel.");

        assertEquals(3, change.getNumPenny(),
                "Change should include 3 pennies.");
    }
    
     @Test
    public void testGetChangeZeroPenny() {
        
        //ARRANGE - combination of zero penny
        Change change = service.getChange(BigDecimal.valueOf(1.15));

        //ACT & ASSERT        
        assertEquals(4, change.getNumQuarter(),
                "Change should include 4 quarters.");

        assertEquals(1, change.getNumDime(),
                "Change should include 1 dime.");

        assertEquals(1, change.getNumNickel(),
                "Change should include 1 nickel.");

        assertEquals(0, change.getNumPenny(),
                "Change should include 0 pennies.");
    }
    

    //test when balance is 0 and deposit is 88
    @Test
    public void testAddMoney() {
        BigDecimal deposit = BigDecimal.valueOf(88);
        BigDecimal balance = BigDecimal.valueOf(0);

        assertEquals(deposit, service.addMoney(balance, deposit),
                "Balance will be the same as deposit if original balance is 0.");
    }

    //test when balance is not zero (is 188) and deposit is 88
    @Test
    public void testAddMoneyWithBalance() {
        BigDecimal deposit = BigDecimal.valueOf(88);
        BigDecimal balance = BigDecimal.valueOf(188);

        assertEquals(BigDecimal.valueOf(276), service.addMoney(balance, deposit),
                "Balance will be the deposit plus original balance if original balance is more than 0.");
    }
}
