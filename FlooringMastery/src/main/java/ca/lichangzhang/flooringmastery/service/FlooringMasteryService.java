package ca.lichangzhang.flooringmastery.service;

import ca.lichangzhang.flooringmastery.dao.FlooringMasteryPersistenceException;
import ca.lichangzhang.flooringmastery.dto.Order;
import ca.lichangzhang.flooringmastery.dto.Product;
import ca.lichangzhang.flooringmastery.dto.Tax;
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
public interface FlooringMasteryService {

    //================== 1. DISPLAY ORDER ======================
    void getFolder() throws FlooringMasteryPersistenceException;

    String getFile(String fileName) throws
            FlooringMasteryNoOrderFileException,
            FlooringMasteryPersistenceException;

    Map<Integer, Order> openFile(String fileName) throws
            FlooringMasteryPersistenceException;

    Integer getAutoOrderNumber() throws
            FlooringMasteryPersistenceException;

    List<Product> loadProduct() throws
            FlooringMasteryPersistenceException;

    List<Tax> loadTax() throws
            FlooringMasteryPersistenceException;

    //==================  ADD / EDIT / REMOVE ORDER ======================
    Order calculateTotal(Order order, List<Tax> taxes, List<Product> products);

    Order addOrder(String fileName, Order order) throws
            FlooringMasteryFutureDateException,
            FlooringMasteryPersistenceException;

    Order editOrder(String fileName, Order order) throws
            FlooringMasteryNoOrderFileException,
            FlooringMasteryNoOrderException,
            FlooringMasteryPersistenceException;

    Order removeOrder(String fileName, int orderNumber) throws
             FlooringMasteryNoOrderFileException,
            FlooringMasteryNoOrderException,
            FlooringMasteryPersistenceException;

    //================== EXPORT ORDER ======================
    void exportData() throws
            FlooringMasteryPersistenceException;
}
