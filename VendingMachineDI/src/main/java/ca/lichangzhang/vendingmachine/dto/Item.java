package ca.lichangzhang.vendingmachine.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author catzh Name: Li Chang Zhang Email: xiaoyuzhang668@gmail.com Date: 2022
 *
 */
public class Item {

    private final int itemNum;
    private String itemName;
    private BigDecimal itemCost;
    private int inventoryNum;

    //set item number to be read only
    public Item(int itemNum) {
        this.itemNum = itemNum;
    }

    public int getItemNum() {
        return itemNum;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getItemCost() {
        return itemCost;
    }

    public void setItemCost(BigDecimal itemCost) {
        this.itemCost = itemCost;
    }

    public int getInventoryNum() {
        return inventoryNum;
    }

    public void setInventoryNum(int inventoryNum) {
        this.inventoryNum = inventoryNum;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.itemNum;
        hash = 97 * hash + Objects.hashCode(this.itemName);
        hash = 97 * hash + Objects.hashCode(this.itemCost);
        hash = 97 * hash + this.inventoryNum;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if (this.itemNum != other.itemNum) {
            return false;
        }
        if (this.inventoryNum != other.inventoryNum) {
            return false;
        }
        if (!Objects.equals(this.itemName, other.itemName)) {
            return false;
        }
        return Objects.equals(this.itemCost, other.itemCost);
    }

    //make item name readonly field, remove setter  for itemName;
    //constructor
    //set for unit testing
    //update toString to display item object
    @Override
    public String toString() {
        //display all items header format
        String fmt = "%-33s %-2s %6s\t\t\t%6s\t";    //define display format   
        return String.format(fmt, itemNum + " -- " + itemName, "$", itemCost, inventoryNum);
    }
}
