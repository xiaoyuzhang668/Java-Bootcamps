package ca.lichangzhang.flooringmastery.dao;

import ca.lichangzhang.flooringmastery.dto.Order;
import ca.lichangzhang.flooringmastery.dto.Product;
import ca.lichangzhang.flooringmastery.dto.Tax;
import ca.lichangzhang.flooringmastery.service.FlooringMasteryFutureDateException;
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
public interface FlooringMasteryDao {

    //==========================METHOD TO GET FOLDER AND GET FILE LIST==========================
    /**
     * Display a List of all order file in the directory.
     *
     *@throws
     * ca.lichangzhang.flooringmastery.dao.FlooringMasteryPersistenceException
     * @throws
     *
     */
    void getFolder() throws FlooringMasteryPersistenceException;

    /**
     * Returns the file name associated with the date of the order user enters
     * Returns null if no such item exists
     *
     * @param fileName file name for the order to be retrieved, user entry of
     * date of the order he/she wants to get
     * @return the String of the order file name associated with the given order
     * date, null if no such file with the order date exists
     * @throws
     * ca.lichangzhang.flooringmastery.dao.FlooringMasteryPersistenceException
     */
    String getFile (String fileName) throws FlooringMasteryPersistenceException;

    /**
     * Returns the list of product object associated with the product. user will
     * need this product map in memory in order to retrieve associate data to
     * put in order associated with the given order
     *
     * @return products product list containing all products information.
     * @throws
     * ca.lichangzhang.flooringmastery.dao.FlooringMasteryPersistenceException
     */
    List<Product> loadProduct() throws FlooringMasteryPersistenceException;

    /**
     * Returns the list of tax object associated with the tax abbreviation.
     * user will need this tax map in memory in order to retrieve associate data
     * to put in order associated with the given order
     *
     * @return taxes tax list containing all tax information.
     * @throws
     * ca.lichangzhang.flooringmastery.dao.FlooringMasteryPersistenceException
     */
    List<Tax> loadTax() throws FlooringMasteryPersistenceException;

    //==========================1. DISPLAY ORDER==========================
    /**
     * Show the specific order after users enters a valid order date and also
     * there is files associated with this order date. Show fileList
     *
     * @param fileName  the order date user enters in order to get the file preferred.
     * @return map orders order list containing all order information.
     * @throws
     * ca.lichangzhang.flooringmastery.dao.FlooringMasteryPersistenceException
     */
    Map<Integer, Order> openFile(String fileName) throws FlooringMasteryPersistenceException;

    //==========================2.  ADD ORDER==========================
    /**
     * Get the auto-generated orderNumber for specific order after users enters
     * a valid order date . if there is no fileName, created a new file and
     * orderNumber set to 1 if file exists, continue with the maximum order
     * number + 1
     *
     * @return orderNumber which is automatically generated based on if the file exists or not
     * @throws
     * ca.lichangzhang.flooringmastery.dao.FlooringMasteryPersistenceException
     */
    Integer getAutoOrderNumber() throws FlooringMasteryPersistenceException;

    /**
     * Automatically add the next integer number as the order number as order
     * placed by user and associates it with the given order object. The order
     * file which saves the order data will be named "order" plus the date the
     * order was made. Such as order placed on current 2022-07-28 will be saved
     * in the file named "order20220728" The order file will be created itself
     * if this is the first order, the subsequent orders on the same date will
     * be saved on this same file. *
     *
     * @param fileName order date which will  be used as file name inside the folder
     * format is Orders_fileName.txt
     * @param order new order object record to be added
     * @return the newly added order object previously associated with the given new order
     * number
     * @throws
     * ca.lichangzhang.flooringmastery.dao.FlooringMasteryFutureDateException
     * ca.lichangzhang.flooringmastery.dao.FlooringMasteryPersistenceException
     */
    Order addOrder(String fileName, Order order) throws 
            FlooringMasteryFutureDateException,
            FlooringMasteryPersistenceException;
  
    //==========================3.  EDIT ORDER==========================
    /**
     * Returns the item object associated with the given order date and given
     * order number. Returns null if no such order date and order exists
     *
     * @param fileName order date which will  be used as file name inside the folder
     * format is Orders_fileName.txt
     * @param order order object to be edited and saved into the order
     * @return order object associated with the given order number, the newly editted order
     * that is being edited null if no such order exists
     * @throws
     * ca.lichangzhang.flooringmastery.dao.FlooringMasteryPersistenceException
     */
    Order editOrder(String fileName, Order order)
            throws FlooringMasteryPersistenceException;

    //==========================4.  REMOVE ORDER==========================
    /**
     * Removes from the order file the order associated with the given order
     * number Returns the order object that is being removed or null if there is
     * no order associated with the given order number
     *
     * @param fileName order date which will  be used as file name inside the folder
     * format is Orders_fileName.txt
     * @param orderNumber orderNumber of order to be removed
     * @return order object that was removed or null if no order was associated
     * with the given order number
     * @throws
     * ca.lichangzhang.flooringmastery.dao.FlooringMasteryPersistenceException
     */
    Order removeOrder(String fileName, int orderNumber) 
            throws FlooringMasteryPersistenceException;

    //==========================5.  EXPORT DATA==========================
    /**
     * Export and save data of order into file
     *
     * @throws
     * ca.lichangzhang.flooringmastery.dao.FlooringMasteryPersistenceException
     */
    void exportData() throws
            FlooringMasteryPersistenceException;
}
