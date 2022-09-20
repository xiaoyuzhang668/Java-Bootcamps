package ca.lichangzhang.vendingmachine.controller;

import ca.lichangzhang.vendingmachine.dao.VendingMachinePersistenceException;
import ca.lichangzhang.vendingmachine.dto.Change;
import ca.lichangzhang.vendingmachine.dto.Item;
import ca.lichangzhang.vendingmachine.service.VendingMachineInsufficientFundsException;
import ca.lichangzhang.vendingmachine.service.VendingMachineNoItemInventoryException;
import ca.lichangzhang.vendingmachine.service.VendingMachineService;
import ca.lichangzhang.vendingmachine.service.VendingMachineZeroDepositException;
import ca.lichangzhang.vendingmachine.ui.VendingMachineView;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author catzh Name: Li Chang Zhang Email: xiaoyuzhang668@gmail.com Date: 2022
 *
 */
public class VendingMachineController {

    //replace the field declaration for the dao with a declaration for the service
    private VendingMachineService service;
    private VendingMachineView view;

    BigDecimal balance;//user balance to be refunded after purchase of item

    public VendingMachineController(VendingMachineService service, VendingMachineView view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;

        try {

            getAll();

            while (keepGoing) {

                menuSelection = getMenuSelection();//prompt user for selection

                switch (menuSelection) {
                    case 1:  //deposit money
                        depositMoney();
                        break;
                    case 2:  //check balance
                        checkBalance();
                        break;
                    case 3:  //display all
                        getAll();
                        displayEnter();
                        break;
                    case 4:  //deposit money
                        purchaseItem();
                        break;
                    case 5:
                        getChange();
                        keepGoing = false;  //exit loop
                        break;
                    default:
                        unknownCommand();
                }
            }
            exitMessage();
        } catch (VendingMachinePersistenceException e) {
            view.displayError(e.getMessage());
        }
    }

    // get menu selection from daoview class, called from controller
    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

    //run different case
    //display all items
    private void getAll() throws VendingMachinePersistenceException {
        view.displayAll();
        List<Item> itemList = service.getAll();//list of inventory   
        view.displayAllItemList(itemList);
    }

    //statement 1 - deposit money
    private void depositMoney() {
        view.displayDeposit();
        //ask user to enter amount of deposit money
        BigDecimal deposit = view.askDeposit();
        balance = service.addMoney(balance, deposit);
        view.displayDepositSuccess(deposit, balance);
    }

    //statement 2 - check balance
    private void checkBalance() {
        view.displayBalance(balance);
    }

    //get item preferred to be purchased
    private BigDecimal purchaseItem() throws VendingMachinePersistenceException {
        getAll();
        view.displayBuy();//display header  
        try {     
                //ask user to input item name to purchase
                int itemNum = view.getItemChoice();
                Item item = service.findItem(itemNum);
                balance = service.purchaseItem(balance, item); 
                view.displayBuySuccess(item);
                getChange();   
                view.displayEnter();
        } catch (  VendingMachineNoItemInventoryException
                | VendingMachineZeroDepositException
                | VendingMachineInsufficientFundsException e) {
            view.displayBreak();
            view.displayError(e.getMessage());
            view.displayEnter();
        }     
        return balance;
    }

    //get change and exit
    private void getChange() {
        if (balance != null) {
        Change change = service.getChange(balance);
        view.displayChange(change);}
    }

    //display unknown command
    private void unknownCommand() {
        view.displayUnknownCommand();
    }

    //display exit message
    private void exitMessage() {
        view.displayExit();
    }
    
    //display enter break
    private void displayEnter() {
        view.displayEnter();
    }
}
