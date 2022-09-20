package ca.lichangzhang.flooringmastery.service;

import ca.lichangzhang.flooringmastery.dao.FlooringMasteryAuditDao;
import ca.lichangzhang.flooringmastery.dao.FlooringMasteryDao;
import ca.lichangzhang.flooringmastery.dao.FlooringMasteryPersistenceException;
import ca.lichangzhang.flooringmastery.dto.Order;
import ca.lichangzhang.flooringmastery.dto.Product;
import ca.lichangzhang.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
public class FlooringMasteryServiceImpl implements FlooringMasteryService {

    private Map<Integer, Order> orders;

    private FlooringMasteryDao dao;
    private FlooringMasteryAuditDao auditDao;

    public FlooringMasteryServiceImpl(
            FlooringMasteryDao dao, FlooringMasteryAuditDao auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;
    }

    //================== 1. DISPLAY ORDER ======================
    @Override
    public void getFolder() throws FlooringMasteryPersistenceException {
        dao.getFolder();
    }

    @Override
    public String getFile(String fileName) throws
            FlooringMasteryNoOrderFileException,
            FlooringMasteryPersistenceException {
        //there is no such order file associated with the parsed date.
        validateOrderFile(fileName);
        return dao.getFile(fileName);  // pass all validation, get the file name}
    }

    @Override
    public Map<Integer, Order> openFile(String fileName) throws
            FlooringMasteryPersistenceException {
        return dao.openFile(fileName);
    }

    @Override
    public List<Product> loadProduct() throws FlooringMasteryPersistenceException {
        return dao.loadProduct();
    }

    @Override
    public List<Tax> loadTax() throws FlooringMasteryPersistenceException {
        return dao.loadTax();
    }
    //================== 2. ADD ORDER ======================

    @Override
    public Integer getAutoOrderNumber() throws
            FlooringMasteryPersistenceException {
        return dao.getAutoOrderNumber();
    }

    @Override
    public Order calculateTotal(Order order, List<Tax> taxes, List<Product> products) {

        BigDecimal area = order.getArea();

        //calculate material and labor cost and set bigdecimal format
        BigDecimal materialCost = area.multiply(order.getProduct().getCostPerSquareFoot()).setScale(2, RoundingMode.HALF_UP);
        BigDecimal laborCost = area.multiply(order.getProduct().getLaborCostPerSquareFoot()).setScale(2, RoundingMode.HALF_UP);

        //set order object member field value
        order.setMaterialCost(materialCost);
        order.setLaborCost(laborCost);

        //calculate tax and total
        BigDecimal taxSub = (materialCost.add(laborCost)).multiply(order.getTax().getTaxRate().divide(BigDecimal.valueOf(100)));

        order.setTaxSub(taxSub.setScale(2, RoundingMode.HALF_UP));

        order.setTotal(materialCost.add(laborCost).add(taxSub).setScale(2, RoundingMode.HALF_UP));

        return order;
    }

    @Override
    public Order addOrder(String fileName, Order order) throws
            FlooringMasteryFutureDateException,
            FlooringMasteryPersistenceException {
        // The add order is successful now write to the audit log
        validateOrderDate(fileName);
        auditDao.writeAuditEntry(
                "Order " + order.getOrderNumber() + " CREATED.");
        return dao.addOrder(fileName, order);
    }

    //================== 3. EDIT ORDER ======================
    @Override
    public Order editOrder(String fileName, Order order) throws
            FlooringMasteryNoOrderFileException,
            FlooringMasteryNoOrderException,
            FlooringMasteryPersistenceException {
        //there is no such order file associated with the parsed date.
        validateOrderFile(fileName);
        validateOrder(fileName, order.getOrderNumber());
        auditDao.writeAuditEntry(
                "Order " + order.getOrderNumber() + " EDITED.");
        return dao.editOrder(fileName, order);
    }

    //================== 4. REMOVE ORDER ======================
    @Override
    public Order removeOrder(String fileName, int orderNumber) throws
            FlooringMasteryNoOrderFileException,
            FlooringMasteryNoOrderException,
            FlooringMasteryPersistenceException {
        //there is no such order file associated with the parsed date.
        validateOrderFile(fileName);
        validateOrder(fileName, orderNumber);
        auditDao.writeAuditEntry(
                "Order " + orderNumber + " REMOVED.");
        return dao.removeOrder(fileName, orderNumber);
    }

    @Override
    public void exportData() throws
            FlooringMasteryPersistenceException {
        dao.exportData();
    }

    //==================== PRIVATE METHOD ======================
    //order date does not exist
    private void validateOrderFile(String fileName) throws
            FlooringMasteryNoOrderFileException,
            FlooringMasteryPersistenceException {
        if (dao.getFile(fileName) == null) {
            throw new FlooringMasteryNoOrderFileException(
                    "ERROR: \tThere is no order placed on the date you entered.  \n\t---" + fileName + "--- does not exist. \n\tThere is no such order file.");
        }
    }

    //order number does not exist
    private void validateOrder(String fileName, Integer orderNumber) throws
            FlooringMasteryNoOrderException,
            FlooringMasteryPersistenceException {
        orders = this.openFile(fileName);
        if (orders.containsKey(orderNumber) == false) {
            throw new FlooringMasteryNoOrderException(
                    "ERROR: \tThe record for the value you entered ---" + orderNumber + "--- does not exist. \n\tThere is no such record.  Please re-enter.");
        }
    }

    //add order date in the future  
    private void validateOrderDate(String fileName) throws
            FlooringMasteryFutureDateException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        // remove all no numeric character if there is any for the file name
        String dateString = fileName.replaceAll("[^\\d]", "");
        LocalDate orderDate = LocalDate.parse(dateString, formatter);
        if (orderDate.compareTo(LocalDate.now()) < 0) {
            //the entry can be parsed, but in the future
            throw new FlooringMasteryFutureDateException(
                    "NOTE:\tThe date entry is earlier than today. \n\tDate should be in the future.");
        }
    }
}
