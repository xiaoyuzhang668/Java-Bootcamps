package ca.lichangzhang.flooringmastery.ui;

import ca.lichangzhang.flooringmastery.dto.Order;
import ca.lichangzhang.flooringmastery.dto.Product;
import ca.lichangzhang.flooringmastery.dto.Tax;
import java.io.IOException;
import static java.lang.String.valueOf;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/*
*
* @author catzh
* Name: Li Chang Zhang
* Email: xiaoyuzhang668@gmail.com
* Date: 2022
* 
 */
public class FlooringMasteryView {

    private UserIO io;

    public FlooringMasteryView(UserIO io) {
        this.io = io;
    }

    private Map<String, Product> products;
    private Map<String, Tax> taxes;

    //====== CONTROLLER LAYER ======
    //display main menu
    public int printMenuAndGetSelection() {

        io.print("***************************************************************************************");
        io.print("* <<Flooring Program>>");
        io.print("***************************************************************************************");
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Export All Data");
        io.print("6. Quit");
        io.print("***************************************************************************************");
        io.print("");

        return io.readInt("Please select the number of the opeation above you wish to perform: ", 1, 6);
    }

    //ending and error  
    public void displayUnknownCommand() {
        io.print("Unknown Command!!");
    }

    public void displayExit() {
        io.print("Good Bye!!!");
    }

    //====== DISPLAY ALL ORDERS ======
    //display file banner
    public void displayAll() {
        io.print("");
        io.print("====================================================================================================================================");
        io.print("******** DISPLAY ALL ORDER FILES ********");
        io.print("-----------------------------------------------------------");
        io.print("The following is all the order files:");
        io.print("------------------------------------------------------------------------------------------------------------------------------------");
        io.print("");
    }

    //get the file name input from user and also return the file name = "Orders_" + user input + .txt
    public String getFileChoice() {
        this.displayBreak2();
        String fileDate = io.readDate("Please enter the date of the order (MMDDYYYY and only number):", "MMddyyyy");
        String fileName = "Orders_" + fileDate + ".txt";
        return fileName;
    }

    //display order banner
    public void displayOrder() {
        io.print("====================================================================================================================================");
        io.print("******** DISPLAY ORDER ********");
        io.print("------------------------------------------------------------------------------------------------------------------------------------");
        io.print("");
        this.displayOrderheader();
    }

    //display order header format
    public void displayOrderheader() {
        String fmt = "%1s %1s %1s   %1s %1s %1s  \t%1s   %1s   \t%1s   \t%1s  \t%1s  \t%1s   %1s%n";    //define display format 
        System.out.format(fmt, "Order Date", "No", " Customer Name", "State", "Tax Rate", "Product", "M.$/ft", "L.$/ft", "Area", "Material $", "Labor $", "Tax", "   Total");
        System.out.format(fmt, "----------", "---", "-------------", "-----", "--------", "-------", "------", "------", "----", "----------", "--------", "-----", "-----");

    }

    //====== ADD ORDER ======
    //display order banner
    public void displayAdd() {
        io.print("====================================================================================================================================");
        io.print("******** ADD ORDER MENU ********");
        io.print("-----------------------------------------------------------");
    }

    //get the file name input from user and date should be later than min today
    public String addDate() {
        io.print("");
        String fileDate = io.readDate("Please enter the date of the order (MMDDYYYY and only number):", LocalDate.now(), "MMddyyyy");
        this.displayBreak2();
        return "Orders_" + fileDate + ".txt";
    }

    //display product banner
    public void displayProduct() {
        io.print("******** PRODUCT LIST ********");
        io.print("");
        String fmt = "%2s  %1s%6s %1s%6s %n";    //define display format 
        System.out.format(fmt, "Product Type", "", "Mat. $/ft", "", "Lab $/ft");
        System.out.format(fmt, "------------", "", "--------", "", "------");
    }

    //display item banner
    public void displayTax() {
        io.print("******** TAX LIST ********");
        io.print("");
        String fmt = "%1s    %2s%n";    //define display format 
        System.out.format(fmt, "State", "Tax Rate");
        System.out.format(fmt, "------", "--------");
    }

    //====== ADD ORDER  ====
    //add order process
    public Order addNewOrder(int orderNumber, List<Tax> taxes, List<Product> products, String fileName) {

        String customerName = io.readStringFormat("Please type customer name:", "Customer Name");
        this.displayBreak2();

        Tax newTax = getTax(taxes);
        Product newProduct = getProduct(products);
        this.displayBreak2();

        //get the member field of order
        BigDecimal area = io.readFloat("How many area you want to buy?", new BigDecimal(valueOf(100)));
        this.displayBreak2();

        //set order object member field value
        Order newOrder = new Order(orderNumber, newProduct, newTax);
        newOrder.setArea(area);
        newOrder.setCustomerName(customerName);

        return newOrder;
    }

    public Tax getTax(List<Tax> taxes) {
        boolean invalidInput = true;
        Tax newTax = null;

        //display state list
        while (invalidInput) {
            print("");
            this.displayTax();
            for (Tax tax : taxes) {
                print(tax);
            }
            io.print("");
            String stateAbbreviation = io.readStringReq("Please type state name (reference to the above state list):", "State Name");

            for (int i = 0; i < taxes.size(); i++) {
                if (taxes.get(i).getStateAbbreviation().equals(stateAbbreviation)) {
                    newTax = new Tax(stateAbbreviation);
                    newTax.setStateName(taxes.get(i).getStateName());
                    newTax.setTaxRate(taxes.get(i).getTaxRate());
                }
            }
            if (newTax == null) {
                // if it explodes, it'll go here and do this
                this.displayError("ERROR: \tInput error. State name ---" + stateAbbreviation + "--- does not exist. \n\tPlease refer to the state list and re-enter.");
            } else {
                invalidInput = false;
            }
        }
        return newTax;
    }

    public Product getProduct(List<Product> products) {
        boolean invalidInput = true;
        Product newProduct = null;

        //display state list
        while (invalidInput) {
            print("");
            this.displayProduct();
            for (Product product : products) {
                print(product);
            }
            io.print("");
            String productType = io.readStringReq("Please type product type (reference to the above product list):", "Product Type");

            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getProductType().equals(productType)) {
                    newProduct = new Product(productType);
                    newProduct.setCostPerSquareFoot(products.get(i).getCostPerSquareFoot());
                    newProduct.setLaborCostPerSquareFoot(products.get(i).getLaborCostPerSquareFoot());
                }
            }
            if (newProduct == null) {
                // if it explodes, it'll go here and do this
                this.displayError("ERROR: \tInput error. Product type ---" + productType + "--- does not exist. \n\tPlease refer to the product list and re-enter.");
            } else {
                invalidInput = false;
            }
        }
        return newProduct;
    }

    public void displayNewOrder(String fileName, Order order) {
        //format fileName into date format to put in console display
        String dateString = fileName.replaceAll("[^\\d]", "");
        io.print("Below is the order details you have entered to add/edit/remove.");
        this.displayBreak();
        this.displayOrderheader();
        io.print(dateString + "   " + order);
        this.displayBreak2();
    }

    public String confirmAdd() {
        io.print("");
        String answer = io.readStringRestrict("Do you want to add this order, answer Yes or No?", "Yes", "No", "Yes/No");
        this.displayBreak2();
        return answer.toLowerCase();
    }

    public String confirmEdit() {
        io.print("");
        String answer = io.readStringRestrict("Do you want to edit this order, answer Yes or No?", "Yes", "No", "Yes/No");
        this.displayBreak2();
        return answer.toLowerCase();
    }

    public String confirmRemove() {
        io.print("");
        String answer = io.readStringRestrict("Do you want to remove this order, answer Yes or No?", "Yes", "No", "Yes/No");
        this.displayBreak2();
        return answer.toLowerCase();
    }

    public void displayAbort() {
        this.displayNote("NOTE:\tYou aborted the action.");
        this.displayBreak2();
    }

    //====== GET ORDER NUMBER ====
    public Integer getOrderNumber(List<Integer> orderList) {
        int orderNumber = io.readIntRestrict("Please enter the order number: ", orderList);
        this.displayBreak2();
        return orderNumber;
    }

    public void displayEmpty() {
        displayNote("NOTE:\tThe file is empty.\n\tThere is no order for this date.");
    }

    //====== EDIT ORDER ======
    public Order editOrder(String fileName, List<Tax> taxes, List<Product> products, Order order) {
        //answerOne - enter customer name
        String customerName = (io.readStringFormatOptional("Change customer name from (" + order.getCustomerName() + ") to?"));

        //prompt user if they want to update customer name
        if ((customerName.trim().length() != 0) && (customerName != null)) {
            order.setCustomerName(customerName);
        }
        this.displayBreak2();

        Tax edittedTax = this.editTax(taxes, order);
        Product edittedProduct = this.editProduct(products, order);

        //answerFour - area
        BigDecimal area = io.readBigDecimal("Change area from (" + order.getArea() + ") to?", new BigDecimal(valueOf(100)));
        //prompt user if they want to update area
        if ((area.compareTo(BigDecimal.ZERO) != 0) && (area.compareTo(BigDecimal.valueOf(100)) >= 0)) {
            order.setArea(area);
        }
        this.displayBreak2();

        return order;
    }

    public Tax editTax(List<Tax> taxes, Order order) {
        boolean invalidInput = true;
        String stateAbbreviation;
        Tax edittedTax = null;
        while (invalidInput) {
            //display state list
            print("");
            this.displayTax();
            for (Tax tax : taxes) {
                print(tax);
            }
            io.print("");

            //answerTwo - state
            stateAbbreviation = (io.readString("Change state name from (" + order.getTax().getStateAbbreviation() + ") to?  (Refer to the tax list above)"));
            //prompt user if they want to update purchase number
            if ((stateAbbreviation.trim().length() != 0) && (stateAbbreviation != null)) {
                for (int i = 0; i < taxes.size(); i++) {
                    if (taxes.get(i).getStateAbbreviation().equals(stateAbbreviation)) {
                        edittedTax = new Tax(stateAbbreviation);
                        edittedTax.setStateName(taxes.get(i).getStateName());
                        edittedTax.setTaxRate(taxes.get(i).getTaxRate());
                        order.setTax(edittedTax);
                    }
                }
                if (edittedTax == null) {
                    // if it explodes, it'll go here and do this
                    this.displayError("ERROR: \tInput error. State name ---" + stateAbbreviation + "--- does not exist. \n\tPlease refer to the state list and re-enter.");
                } else {
                    invalidInput = false;
                }
            } else {
                break;
            }
            this.displayBreak2();
        }
        return edittedTax;
    }

    public Product editProduct(List<Product> products, Order order) {
        boolean invalidInput = true;
        String productType;
        Product edittedProduct = null;
        while (invalidInput) {
            //display state list
            print("");
            this.displayProduct();
            for (Product product : products) {
                print(product);
            }
            io.print("");

            //answerTwo - state
            productType = (io.readString("Change product type from (" + order.getProduct().getProductType() + ") to?  (Refer to the product list above)"));
            //prompt user if they want to update purchase number
            if ((productType.trim().length() != 0) && (productType != null)) {
                for (int i = 0; i < products.size(); i++) {
                    if (products.get(i).getProductType().equals(productType)) {
                        edittedProduct = new Product(productType);
                        edittedProduct.setCostPerSquareFoot(products.get(i).getCostPerSquareFoot());
                        edittedProduct.setLaborCostPerSquareFoot(products.get(i).getLaborCostPerSquareFoot());
                        order.setProduct(edittedProduct);
                    }
                }
                if (edittedProduct == null) {
                    // if it explodes, it'll go here and do this
                    this.displayError("ERROR: \tInput error. Product type ---" + productType + "--- does not exist. \n\tPlease refer to the product list and re-enter.");
                } else {
                    invalidInput = false;
                }
            } else {
                break;
            }
            this.displayBreak2();
        }
        return edittedProduct;
    }

    public void displaySuccess(String fileName, Order order) {

        io.print("");
        io.print("");
        io.print("******* Order Detail (added/editted/removed) ********");
        io.print("Order Date:\t" + LocalDate.parse(fileName.replaceAll("[^\\d]", ""), DateTimeFormatter.ofPattern("MMddyyyy"))
                + "\tOrder Number:\t" + order.getOrderNumber() + "\tCustomer Name:\t" + order.getCustomerName()
                + "\nState:\t\t" + order.getTax().getStateAbbreviation() + "\t\tTax Rate:\t" + order.getTax().getTaxRate()
                + "\n\nProduct:   " + order.getProduct().getProductType() + "\tMaterial Cost Per Square Foot:\t$" + order.getProduct().getCostPerSquareFoot() + "\tLabor Cost Per Square Foot: $" + order.getProduct().getLaborCostPerSquareFoot()
                + "\nArea:   " + order.getArea() + "\tMaterial Cost:\t\t\t$" + order.getMaterialCost() + "\tLabor Cost:\t$" + order.getLaborCost()
                + "\nTax: $" + order.getTaxSub() + "\tTotal:\t\t$" + order.getTotal());
        io.print("");
        this.displayNote("NOTE:\tOrder ---" + order.getOrderNumber() + "--- has been successfully added/edited/removed.");
    }

    //display add item banner
    public void displayExport() {
        io.print("");
        io.print("====================================================================================================================================");
        io.print("******** EXPORT ALL DATA ********");
        this.displayBreak();
    }

    //print method to print out item selected 
    public void print(Order order) {
        System.out.println(order);
    }

    //print method to print out item selected 
    public void print(Product product) {
        System.out.println(product);
    }

    //print method to print out item selected 
    public void print(Tax tax) {
        System.out.println(tax);
    }

    // display break line
    public void displayBreak() {
        io.print("-----------------------------------------------------------------------------------------------------------------------------------");
        io.print("");
    }

    // display break line
    public void displayBreak2() {
        io.print("====================================================================================================================================");
        io.print("");
    }

    public void displayError(String errorMsg) {
        io.print("");
        io.print("******** FLOORING MASTERY ERROR ********");
        io.print(errorMsg);
        this.displayEnter();
    }

    public void displayInvalid(String errorMsg) {
        io.print("");
        io.print("******** FLOORING MASTERY ERROR ********");
        io.print(errorMsg);
        this.displayBreak();
    }

    public void displayNote(String errorMsg) {
        io.print("");
        io.print("******** FLOORING MASTERY NOTE ********");
        io.print(errorMsg);
        this.displayEnter();
    }

    public void print(String message) {
        io.print(message);
    }

    public void printOne(String message) {
        io.printOneLine(message);
    }

    public void printOne(String msg, String delimiter) {
        io.printOneLine(msg + delimiter);
    }

    //method to   prompt user enter to continue    // display break line
    public void displayEnter() {
        io.print("====================================================================================================================================");
        io.print("");
        io.print("Please hit enter to continue");
        try {
            System.in.read();
        } catch (IOException e) {
        }
    }
}
