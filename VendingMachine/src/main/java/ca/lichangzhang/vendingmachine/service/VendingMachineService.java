package ca.lichangzhang.vendingmachine.service;

import ca.lichangzhang.vendingmachine.dao.VendingMachinePersistenceException;
import ca.lichangzhang.vendingmachine.dto.Change;
import ca.lichangzhang.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;

/*
*
* @author catzh
* Name: Li Chang Zhang
* Email: xiaoyuzhang668@gmail.com
* Date: 2022
* 
 */
public interface VendingMachineService {

    BigDecimal purchaseItem(BigDecimal deposit, Item item) throws
            VendingMachineZeroDepositException,
            VendingMachineInsufficientFundsException,
            VendingMachineNoItemInventoryException,
            VendingMachinePersistenceException;

    List<Item> getAll() throws
            VendingMachinePersistenceException;
    
    Item findItem(int itemNum) throws
            VendingMachinePersistenceException;
    
     /**
     * A method to convert the balance of user deposit money into
     * number of quarter, dime, nickel and penny
     *
     * Returns an object of combination of coin
     *
     * @param balance the amount of deposit money user have in account balance
     *  
     * @return change object of coin combination 
     *
     */
    Change getChange(BigDecimal balance);

     /**
     * A method to allow user to deposit money and update the balance of his account money
     *- allow user to deposit money which amount is bigger than 0 and return the
     * balance of his account
     *
     * Returns from the account of his balance
     *
     * @param deposit the amount of deposit money user enters
     * @param balance the original balance of user money account before he deposits money
     * 
     * @return total balance of user money account after new deposit
     *
     */
    BigDecimal addMoney(BigDecimal balance, BigDecimal deposit);  
}
