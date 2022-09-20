package ca.lichangzhang.vendingmachine.service;

import ca.lichangzhang.vendingmachine.dao.VendingMachineAuditDao;
import ca.lichangzhang.vendingmachine.dao.VendingMachineDao;
import ca.lichangzhang.vendingmachine.dao.VendingMachinePersistenceException;
import ca.lichangzhang.vendingmachine.dto.Change;
import ca.lichangzhang.vendingmachine.dto.Coin;
import ca.lichangzhang.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/*
*
* @author catzh
* Name: Li Chang Zhang
* Email: xiaoyuzhang668@gmail.com
* Date: 2022
* 
 */
// Importing input output classes
public class VendingMachineServiceImpl implements 
        VendingMachineService {

    private VendingMachineDao dao;
    private VendingMachineAuditDao auditDao;

    public VendingMachineServiceImpl(
            VendingMachineDao dao, VendingMachineAuditDao auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;
    }
    // change initialization;
    private Change change = new Change();

    @Override
    public BigDecimal purchaseItem(BigDecimal balance, Item item) throws
            VendingMachineNoItemInventoryException,
            VendingMachineZeroDepositException,
            VendingMachineInsufficientFundsException,
            VendingMachinePersistenceException {

        //exception - zero inventory. if inventory with this item is 0, throw VendingMachineNoItemInventoryException
        validateZeroInventory(item);

        //exception - zero or null deposit money
        validateZeroDeposit(balance, item);

        //exception - not enough money to buy the item
        validateEnoughMoney(balance, item);

        //if passing all the business rules checks, go ahead to deduct one from inventory and deduct item cost from balance and persist the item record.
        balance = dao.purchaseItem(balance, item.getItemNum());
        //user has successfully purchased the item, then inventory number for this item has been reduced by one
        //persist the item object an create audit log
        auditDao.writeAudit("Item " + item.getItemName() + " SOLD.");
        return balance;
    }

    @Override
    public List<Item> getAll() throws
            VendingMachinePersistenceException {
        return dao.getAll(); //pass through method, no change
    }

    @Override
    public Item findItem(int itemNum) throws
            VendingMachinePersistenceException {
        return dao.findItem(itemNum);
    }

    @Override
    public Change getChange(BigDecimal balance) {
        //get BigDecimal value of each coin   
        change.setBalance(balance);
        final BigDecimal Q = Coin.QUARTERS.getValue();
        final BigDecimal D = Coin.DIMES.getValue();
        final BigDecimal N = Coin.NICKELS.getValue();
        final BigDecimal P = Coin.PENNY.getValue();
        //define list coin
        List<Integer> currencyList = new ArrayList<>();
        //retrieve value of each coin
        List<BigDecimal> currency = List.of(Q, D, N, P); // list of coin value
        BigDecimal[] combination; // array to hold the currency coin combination

        for (int i = 0; i < currency.size(); i++) {
            combination = balance.divideAndRemainder(currency.get(i)); // get array of how many quarter and remainder
            //add number of coin to array combination
            currencyList.add(combination[0].intValue());
            balance = combination[1]; //get the remaider as rest of changePenny to be converted into the next coin type 
        }

        change.setNumQuarter(currencyList.get(0));
        change.setNumDime(currencyList.get(1));
        change.setNumNickel(currencyList.get(2));
        change.setNumPenny(currencyList.get(3));

        return change; // return the change object
    }

    @Override
    public BigDecimal addMoney(BigDecimal balance, BigDecimal deposit) {
        if (balance == null || balance.compareTo(BigDecimal.ZERO) == 0) {
            balance = deposit;
        } else {
            balance = balance.add(deposit);
        }
        return balance;
    }

    private void validateZeroInventory(Item item) throws
            VendingMachineNoItemInventoryException {
        //exception - zero inventory. if inventory with this item is 0, return null, throw VendingMachineNoItemInventoryException
        if (item.getInventoryNum() == 0) {
            throw new VendingMachineNoItemInventoryException(
                    "=== ERROR: Zero Inventory ===\nERROR: \tThe inventory for the item number " + item.getItemNum() + " is zero.  \n\tThis item ---"
                    + item.getItemName() + "--- is not available. It has zero inventory. \n\tPlease go back and select another item.");
        }
    }

    private void validateZeroDeposit(BigDecimal balance, Item item) throws
            VendingMachineZeroDepositException {
        //exception - zero or null deposit money
        if ((balance == null) || (balance.compareTo(BigDecimal.ZERO) == 0)) {
            throw new VendingMachineZeroDepositException("=== ERROR: Zero Deposit ===\nERROR: \tYou have not deposited any money yet, \n\tso you are not allowed to purchase the item selected --" + item.getItemName()
                    + "---.\n\tPlease go back and deposit money first by selecting 1.");
        }
    }

    private void validateEnoughMoney(BigDecimal balance, Item item) throws
            VendingMachineInsufficientFundsException {
        //exception - not enough money to buy the item
        int reComp = balance.compareTo(item.getItemCost());
        //evaludate result
        if (reComp == -1) {
            throw new VendingMachineInsufficientFundsException(
                    "=== ERROR: Insufficient Deposit ===\nERROR: \tYou do not have enough deposit to buy this item.  \n\tItem --- " + item.getItemName() + "--- costs $"
                    + item.getItemCost() + " and you only have balance of $"
                    + balance + ". \n\t$" + item.getItemCost().subtract(balance) + " more is needed in order to buy this item. \n\tplease go back and enter 1 to add more money.");
        }
    }
}
