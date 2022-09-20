package ca.lichangzhang.vendingmachine.service;

import ca.lichangzhang.vendingmachine.dao.VendingMachineDao;
import ca.lichangzhang.vendingmachine.dao.VendingMachinePersistenceException;
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
public class VendingMachineDaoStubImpl implements VendingMachineDao {

    //one and only one Item in DAOStub
    //two Constructor - 
    //One is a no-arg constructor that instantiates a hard-coded item for our Stub
    //The other allows a test item to be injected via the constructor by a test class.
    public Item onlyItem;
//
    public VendingMachineDaoStubImpl() {
        onlyItem = new Item(1);
        onlyItem.setItemName("Glitter Potion");
        onlyItem.setItemCost(BigDecimal.valueOf(2.75));
        onlyItem.setInventoryNum(5);
    }

    public VendingMachineDaoStubImpl(Item testItem) {
        this.onlyItem = testItem;
    }

    @Override
    public List<Item> getAll()
            throws VendingMachinePersistenceException {
        List<Item> itemList = new ArrayList<Item>();
        itemList.add(onlyItem);
        return itemList;
    }

    @Override
    public Item findItem(int itemNum)
            throws VendingMachinePersistenceException {
        if (itemNum == onlyItem.getItemNum()) {
            return onlyItem;
        } else {
            return null;
        }
    }

    @Override
    public BigDecimal purchaseItem(BigDecimal balance, int itemNum)
            throws VendingMachinePersistenceException {
        if (balance.compareTo(onlyItem.getItemCost()) == 1
                && onlyItem.getInventoryNum() != 0 && (balance.compareTo(BigDecimal.ZERO) != 0)) {
            balance.subtract(onlyItem.getItemCost());
        }
        return balance;
    }  
}
