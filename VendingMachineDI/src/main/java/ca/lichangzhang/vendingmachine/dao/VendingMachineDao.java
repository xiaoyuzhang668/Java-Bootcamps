package ca.lichangzhang.vendingmachine.dao;

import ca.lichangzhang.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author catzh Name: Li Chang Zhang Email: xiaoyuzhang668@gmail.com Date: 2022
 *
 */
public interface VendingMachineDao {

    /**
     * A method to update inventory record of a specific item after user
     * purchase the item successfully.The application prevents user from buying
     * an item if he/she does not deposit any money.After deposit of money, if
     * the price of the item user selected is more than the deposit money,
     * application will prompt user to return back main menu to add more money.
     *
     * If the item user selected has zero inventory, application will prevent
     * user from buying the item.
     *
     * Returns from the inventory the item object with user deposit more than
     * the item price after user successfully purchase this item. Returns null
     * if associated item has zero inventory or user does not have enough money
     * to buy this item. Return the item object that is being bought and edited
     * or null if user does not have sufficient money or item has zero
     * inventory.
     *
     * @param balance money user has in his balance to buy the item
     * @param itemNum item number of the object to be bought
     *
     * deduct 1 from inventory if purchase is successful
     * @return balance - the balance after item being purchased successfully,
     * @throws
     * ca.lichangzhang.vendingmachine.dao.VendingMachinePersistenceException
     *
     */
    BigDecimal purchaseItem(BigDecimal balance, int itemNum) throws
            VendingMachinePersistenceException;

    /**
     * Returns a List of all item in the inventory.
     *
     * @return map containing all items in the inventory.
     * @throws
     * ca.lichangzhang.vendingmachine.dao.VendingMachinePersistenceException
     *
     */
    List<Item> getAll() throws
            VendingMachinePersistenceException;

    /**
     * Returns the item object associated with the given item number.Returns
     * null if no such item exists
     *
     * @param itemNum item number of the item to retrieve
     * @return the item object associated with the given item number, null if no
     * such item exists
     * @throws
     * ca.lichangzhang.vendingmachine.dao.VendingMachinePersistenceException
     */
    Item findItem(int itemNum) throws
            VendingMachinePersistenceException;
}
