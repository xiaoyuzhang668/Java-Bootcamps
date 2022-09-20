package ca.lichangzhang.flooringmastery.controller;

import ca.lichangzhang.flooringmastery.dao.FlooringMasteryPersistenceException;
import ca.lichangzhang.flooringmastery.dto.Order;
import ca.lichangzhang.flooringmastery.dto.Product;
import ca.lichangzhang.flooringmastery.dto.Tax;
import ca.lichangzhang.flooringmastery.service.FlooringMasteryFutureDateException;
import ca.lichangzhang.flooringmastery.service.FlooringMasteryNoOrderException;
import ca.lichangzhang.flooringmastery.service.FlooringMasteryNoOrderFileException;
import ca.lichangzhang.flooringmastery.service.FlooringMasteryService;
import ca.lichangzhang.flooringmastery.ui.FlooringMasteryView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.springframework.util.CollectionUtils.isEmpty;

/*
** @author catzh
* Name: Li Chang Zhang
* Email: xiaoyuzhang668@gmail.com
* Date: 2022
* 
 */
public class FlooringMasteryController {

    Map<Integer, Order> orders;
    List<Product> products;
    List<Tax> taxes;
    String fileName;

    //replace the field declaration for the dao with a declaration for the service
    private FlooringMasteryService service;
    private FlooringMasteryView view;

    public FlooringMasteryController(FlooringMasteryService service,
            FlooringMasteryView view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
//        initialize();

        boolean keepGoing = true;
        int menuSelection = 0;

        try {
            taxes = service.loadTax();
            products = service.loadProduct();
            while (keepGoing) {
                menuSelection = getMenuSelection();//prompt user for selection

                switch (menuSelection) {
                    case 1:  //display all
                        displayAll();
                        break;
                    case 2:  //add order
                        addOrder();
                        break;
                    case 3:  //edit order
                        editOrder();
                        break;
                    case 4:  // remove order
                        removeOrder();
                        break;
                    case 5:
                        exportData();
                        break;
                    case 6:
                        keepGoing = false;  //exit loop
                        break;
                    default:
                        unknownCommand();
                }
            }
            exitMessage();
        } catch (FlooringMasteryPersistenceException e) {
            view.displayError(e.getMessage());
        }
    }

    // get menu selection from daoview class, called from controller
    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

    //menu option 1 ======DISPLAY ALL ORDER ====== getFile
    private void displayAll() throws FlooringMasteryPersistenceException {
        view.displayAll();
        service.getFolder(); // display order file names in the folder
        fileName = view.getFileChoice(); // user enter order date
        try {
            service.getFile(fileName);
            view.displayOrder();
            orders = service.openFile((fileName));
            view.displayEnter();
        } catch (FlooringMasteryNoOrderFileException e) {
            view.displayError(e.getMessage());
        }
    }

    //menu option 2 ====== ADD AN ORDER ====== addOrder
    private void addOrder() throws
            FlooringMasteryPersistenceException {
        view.displayAdd();
        fileName = view.addDate();  //validate date to be future
        try {
            int orderNumber = service.getAutoOrderNumber();
            Order newOrder = view.addNewOrder(orderNumber, taxes, products, fileName);
            service.calculateTotal(newOrder, taxes, products);
            view.displayNewOrder(fileName, newOrder);
            if (view.confirmAdd().equalsIgnoreCase("yes")) {
                service.addOrder(fileName, newOrder);
                view.displaySuccess(fileName, newOrder);
            } else {
                view.displayAbort();
            }
        } catch (FlooringMasteryFutureDateException e) {
            view.displayError(e.getMessage());
        }
    }

    // //menu option 3 ====== EDIT AN ORDER ====== editOrder
    private void editOrder() throws FlooringMasteryPersistenceException {//
        view.displayAll();
        service.getFolder(); // display order file names in the folder
        fileName = view.getFileChoice(); // user enter order date
        try {
            service.getFile(fileName);
            view.displayOrder();
            orders = service.openFile((fileName));
            List<Integer> orderList = new ArrayList<>();
            orderList.addAll(orders.keySet());
            if (isEmpty(orderList)) {
                view.displayEmpty();
                return;
            }
            view.displayBreak2();
            //ask user to enter orderNumber
            int orderNumber = view.getOrderNumber(orderList);
            Order edittedOrder = orders.get(orderNumber);
            //get editted order
            edittedOrder = view.editOrder(fileName, taxes, products, edittedOrder);
            //calculate member fields
            service.calculateTotal(edittedOrder, taxes, products);
            view.displayNewOrder(fileName, edittedOrder);
            //svae record
            if (view.confirmEdit().equals("yes")) {
                service.editOrder(fileName, edittedOrder);
                view.displaySuccess(fileName, edittedOrder);
            } else {
                view.displayAbort();
            }
        } catch (FlooringMasteryNoOrderFileException
                | FlooringMasteryNoOrderException e) {
            view.displayError(e.getMessage());
        }
    }

    //menu option 4 ====== REMOVE AN ORDER ====== removeOrder
    private void removeOrder() throws FlooringMasteryPersistenceException {
        view.displayAll();
        service.getFolder(); // display order file names in the folder
        fileName = view.getFileChoice(); // user enter order date
        try {
            service.getFile(fileName);
            view.displayOrder();
            orders = service.openFile((fileName));
            view.displayBreak();
            List<Integer> orderList = new ArrayList<>();
            orderList.addAll(orders.keySet());
            if (isEmpty(orderList)) {
                view.displayEmpty();
                return;
            }
            //ask user to enter orderNumber
            int orderNumber = view.getOrderNumber(orderList);
            view.displayNewOrder(fileName, orders.get(orderNumber));
            if (view.confirmRemove().equals("yes")) {
                Order removeOrder = service.removeOrder(fileName, orderNumber);
                view.displaySuccess(fileName, removeOrder);
            } else {
                view.displayAbort();
            }
        } catch (FlooringMasteryNoOrderFileException
                | FlooringMasteryNoOrderException e) {
            view.displayError(e.getMessage());
        }
    }

    //menu option 5 ====== exportData ====== exportData
    private void exportData() throws
            FlooringMasteryPersistenceException {
        view.displayExport();
        try {
            service.exportData();
        } catch (FlooringMasteryPersistenceException ex) {
            view.displayError(ex.getMessage());
        }
        view.displayEnter();
    }

    //display unknown command
    private void unknownCommand() {
        view.displayUnknownCommand();
    }

    //display exit message
    private void exitMessage() {
        view.displayExit();
    }
}
