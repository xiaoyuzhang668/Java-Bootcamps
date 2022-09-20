package ca.lichangzhang.vendingmachine.dao;

import ca.lichangzhang.vendingmachine.dto.Item;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author catzh Name: Li Chang Zhang Email: xiaoyuzhang668@gmail.com Date: 2022
 *
 */

public class VendingMachineDaoFileImpl implements VendingMachineDao {

    private Map<Integer, Item> items = new HashMap<>();

    // list initialization;
    private List<Item> itemList = new ArrayList<>(items.values());

    //use different implemention, constructor for testing
    private final String ITEM_FILE;

    private static final String DELIMITER = "::";

    public VendingMachineDaoFileImpl() {
        ITEM_FILE = "inventory.txt";
    }

    //different constructor
    public VendingMachineDaoFileImpl(String testFile) {
        ITEM_FILE = testFile;
    }

    @Override
    public List<Item> getAll() throws VendingMachinePersistenceException {
        loadItem();
        //return array list        
        return new ArrayList<Item>(items.values());
    }

    @Override
    public Item findItem(int itemNum) throws
            VendingMachinePersistenceException {
        loadItem();
        return items.get(itemNum);
    }

    @Override
    public BigDecimal purchaseItem(BigDecimal balance, int itemNum) throws 
            VendingMachinePersistenceException {
        loadItem();
        Item item = items.get(itemNum); //get the item to be purchased
        item.setInventoryNum(item.getInventoryNum() - 1);//deduct 1 from inventory number
        balance = balance.subtract(item.getItemCost()); // get balance after deducting item cost from balance
        itemList = new ArrayList<>(items.values());
        writeItem();
        return balance;
    }

    //PRIVATE METHOD S
    // MARSHALL AND UNMARSHALL 
    //method to persisten object and retrieve file into object memory
    private Item unmarshallItem(String itemAsText) {
        /*
        read text file itemAsText, one line to one line and
        finally change into object itemFromFile 
         */
        String[] indexArray = itemAsText.split(DELIMITER);
        //itemNum is in index of 0 of the array
        int itemNum = Integer.parseInt(indexArray[0]);
        //crate a new item object
        Item itemFromFile = new Item(itemNum);
        //add the remaining index array into the item object
        //convert string into BigDecimal then set value
        itemFromFile.setItemName(indexArray[1]);
        itemFromFile.setItemCost(new BigDecimal(indexArray[2]));
        //convernt string into integer then set value
        itemFromFile.setInventoryNum(Integer.parseInt(indexArray[3].trim()));
        //create items object and return object to form map
        return itemFromFile;
    }

    /*
    read from file line by line, unmarshallItem, then form map object in memory
    @throws VendingMachineDaoException if error occurs reading from the file
     */
    private void loadItem() throws VendingMachinePersistenceException {
        Scanner scanner;
        //create scanner for reading file
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(ITEM_FILE)));
        } catch (FileNotFoundException e) {
            throw new VendingMachinePersistenceException("-_- Could not load item data into memory.", e);
        }
        //currentLine holds the most recent line read from file
        String currentLine;
        //currentItem holds the most recent item unmarshalled
        Item currentItem;
        //go through ITEM_FILE line by line, decoding each line into a
        //item object by calling the unmarshallingItem method
        //process while having more lines
        while (scanner.hasNextLine()) {
            //get the next line in the file
            currentLine = scanner.nextLine();
            //unmarshall the line into a item object
            currentItem = unmarshallItem(currentLine);

            //we are going to use the itemNum as the map key for the Item object
            //put currentItem into map using itemNum as key
            items.put(currentItem.getItemNum(), currentItem);
        }
        //close scanner
        scanner.close();
    }

    /*
    translate record from map object memory into file and save it, save string text array 
     */
    private String marshallItem(Item currentItem) {
        //we need to trun current item object into a line of text for our file
        String itemAsText = currentItem.getItemNum() + DELIMITER;
        //add the rest of the properties in the correct order;
        //item name
        itemAsText += currentItem.getItemName() + DELIMITER;
        //itemCost
        itemAsText += currentItem.getItemCost() + DELIMITER;
        //invenotryNum
        itemAsText += currentItem.getInventoryNum();
        //we have now turned an item object into text, retur it
        return itemAsText;
    }

    /*
     write all item in the map object memory out a a ITEM_FILE
     @throws VendingMachinePersistenceException if an error occurs written to the file
     */
    private void writeItem() throws VendingMachinePersistenceException {
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(ITEM_FILE));
        } catch (IOException e) {
            throw new VendingMachinePersistenceException("Could not save item data into the inventory.", e);
        }
        String itemAsText;

        //convert list into hashMap object. lambdas function
//        Map<String, Item> map = new HashMap<>();
//        for (Item i : itemList) {
//            map.put(i.getItemName(), i);
//        }
        for (Item currentItem : itemList) {
            //turn an item object into a String
            itemAsText = marshallItem(currentItem);
            //write the item object to the file
            out.println(itemAsText);
            //force PrintWriter to write line to the file
            out.flush();
        }
        //clear upS
        out.close();
    }
}
