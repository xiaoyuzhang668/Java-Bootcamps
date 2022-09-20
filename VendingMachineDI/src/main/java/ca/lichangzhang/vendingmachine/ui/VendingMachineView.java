package ca.lichangzhang.vendingmachine.ui;

import ca.lichangzhang.vendingmachine.dto.Change;
import ca.lichangzhang.vendingmachine.dto.Item;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author catzh Name: Li Chang Zhang Email: xiaoyuzhang668@gmail.com Date: 2022
 *
 */

public class VendingMachineView {

    private UserIO io;

 
    public VendingMachineView(UserIO io) {
        this.io = io;
    }

    //====== CONTROLLER LAYER ======
    //display main menu
    public int printMenuAndGetSelection() {
        io.print("========================================================================================");
        io.print("******** Main Menu ********");
        io.print("---------------------------------------------------------------------------------------");
        io.print("1. Deposit Money");
        io.print("2. Check Balance");
        io.print("3. Display All Items");
        io.print("4. Purchase Item");
        io.print("5. Exit");
        io.print("============================================================");
        io.print("");

        return io.readInt("Please select the number of the opeation above you wish to perform: ", 1, 5);
    }

    //ending and error  
    public void displayUnknownCommand() {
        io.print("Unknown Command!!");
    }

    public void displayExit() {
        io.print("Good Bye!!!");
    }

    //====== DEPOSIT MONEY ======
    //display deposit money banner
    public void displayDeposit() {
        io.print("========================================================================================");
        io.print("******** DEPOSIT MONEY ********");
    }

    //prompt for money input, use method double in view 
    public BigDecimal askDeposit() {
        BigDecimal deposit = io.readBigDecimal("Please enter how much dollars you want to deposit for item purchase:", BigDecimal.valueOf(0));
        return deposit;
    }

    //display deposit money successfully
    public void displayDepositSuccess(BigDecimal deposit, BigDecimal balance) {
        io.print("----------------------------------------------------------------------------------------");
        io.print("You have successfully depositted the money of $" + deposit + ".");
        io.print("Your balance is: $" + balance + ".");
        io.print("========================================================================================");
        io.print("");
        io.readString("Please hit enter to continue.");
    }

    //====== CHECK BALANCE ====
    //display check balance banner
    public void displayBalance(BigDecimal balance) {
        io.print("========================================================================================");
        io.print("******** CHECK BALANCE ********");
        if (balance == null) {
            io.print("Your balance is: 0.");
        } else {
            io.print("Your balance is: $" + balance + ".");
        }
        io.print("========================================================================================");
        io.print("");
        io.readString("Please hit enter to continue.");
    }

    //====== DISPLAY ALL ======
    //display items banner
    public void displayAll() {
        io.print("========================================================================================");
        io.print("******** ITEM LIST ********");
        String fmt = "%-36s %-1s %6s\t%6s\t%n";    //define display format 
        System.out.format(fmt, "Item Name", "", "Cost", "       No. of Inventory");
        System.out.format(fmt, "----------------------------", "", "----------", "------------");
    }

    public void displayAllItemList(List<Item> itemList) {
        String fmt = "%-2s \t %-28s %-1s %6s\t\t%6s";
        //lambda function to print out item list
        //only get item which inventory number is more than 0
        itemList.stream()
                .filter(item -> item.getInventoryNum() > 0)
                .forEach((item)
                        -> io.print(String.format(fmt,
                        item.getItemNum(),
                        item.getItemName(),
                        "$",
                        item.getItemCost(),
                        item.getInventoryNum())));
    }

    //====== GET ITEM NUMBER ====
    public int getItemChoice() {
        return io.readInt("Please enter the item number you want to purchase:", 1, 10);
    }

    //====== PURCHASE ITEM ======
    //display find result
    public void displayBuy() {
        io.print("========================================================================================");
        io.print("******** PURCHASE ITEM ********");
    }

    // display break line
    public void displayBreak() {
        io.print("----------------------------------------------------------------------------------------");
        io.print("");
    }

    //display successful purchase
    public void displayBuySuccess(Item item) {
        io.print("");
        io.print("You have successfully purchased the item of " + item.getItemName() + ".");
    }

    //====== GET CHANGE ======
    public void displayChange(Change change) {
        io.print("Your balance is $" + change.getBalance() + " in total. ");
        io.print("That is equal to:");
        io.print("---------------------------");
        io.print(change.getNumQuarter() + " Quarter ");
        io.print(change.getNumDime() + " Dime ");
        io.print(change.getNumNickel() + " Nickel ");
        io.print(change.getNumPenny() + " Penny.");
        io.print("");
    }

    //====== EXIT ======    
    public void displayError(String errorMsg) {
        io.print("******** VENDING MACHINE ERROR ********");
        io.print(errorMsg);
    }

    public void print(String message) {
        io.print(message);
    }

    //method to   prompt user enter to continue    // display break line
    public void displayEnter() {
        io.print("========================================================================================");
        io.print("");
        io.print("Please hit enter to continue");
        try {
            System.in.read();
        } catch (IOException e) {
        }
    }
}
