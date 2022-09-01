package ca.lichangzhang.flooringmastery.service;

import ca.lichangzhang.flooringmastery.dao.FlooringMasteryDao;
import ca.lichangzhang.flooringmastery.dao.FlooringMasteryPersistenceException;
import ca.lichangzhang.flooringmastery.dto.Order;
import ca.lichangzhang.flooringmastery.dto.Product;
import ca.lichangzhang.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
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
public class FlooringMasteryDaoStubImpl implements FlooringMasteryDao {

    public Order onlyOrder;
    public Tax onlyTax;
    public Product onlyProduct;
    public String onlyFileName;
    Map<Integer, Order> orders = new HashMap<>();

    public FlooringMasteryDaoStubImpl() {
        onlyProduct = new Product("Iron", new BigDecimal(3.01), new BigDecimal(2.01));

        onlyTax = new Tax("IN", "Indiana", new BigDecimal(1.01));

        onlyFileName = "02022033";
        onlyOrder = new Order(88);
        onlyOrder.setCustomerName("Amy Cheung");
        onlyOrder.setTax(onlyTax);
        onlyOrder.setProduct(onlyProduct);
        onlyOrder.setArea(new BigDecimal(100.02));
        onlyOrder.setMaterialCost(new BigDecimal(301.06));
        onlyOrder.setLaborCost(new BigDecimal(201.04));
        onlyOrder.setTaxSub(new BigDecimal(5.07));
        onlyOrder.setTotal(new BigDecimal(507.17));

        orders.put(onlyOrder.getOrderNumber(), onlyOrder);
    }

    public FlooringMasteryDaoStubImpl(Order testOne) {
        this.onlyOrder = testOne;
    }

    @Override
    public void getFolder() throws FlooringMasteryPersistenceException {
        List<String> fileList = List.of("Orders_" + onlyFileName);
    }

    @Override
    public String getFile(String fileName) throws FlooringMasteryPersistenceException {
        if (fileName.equals(onlyFileName)) {
            return onlyFileName;
        } else {
            return null;
        }
    }

    @Override
    public List<Product> loadProduct() throws FlooringMasteryPersistenceException {
        return Arrays.asList(onlyProduct);
    }

    @Override
    public List<Tax> loadTax() throws FlooringMasteryPersistenceException {
        return Arrays.asList(onlyTax);
    }

    @Override
    public Map<Integer, Order> openFile(String fileName) throws FlooringMasteryPersistenceException {
        if (fileName.equals(onlyFileName)) {
            return orders;
        } else {
            return null;
        }
    }

    @Override
    public Integer getAutoOrderNumber() throws FlooringMasteryPersistenceException {
        return onlyOrder.getOrderNumber() + 1;
    }

    @Override
    public Order addOrder(String fileName, Order order) throws FlooringMasteryPersistenceException {
        if (fileName.equals(onlyFileName) && (onlyOrder.getOrderNumber() == order.getOrderNumber())) {
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public Order editOrder(String fileName, Order order) throws FlooringMasteryPersistenceException {
        if (fileName.equals(onlyFileName) && (onlyOrder.getOrderNumber() == order.getOrderNumber())) {
            return order;
        } else {
            return null;
        }
    }

    @Override
    public Order removeOrder(String fileName, int orderNumber) throws FlooringMasteryPersistenceException {
        Map<Integer, Order> orders = new HashMap<>();
        orders.put(onlyOrder.getOrderNumber(), onlyOrder);
        if (fileName.equals(onlyFileName) && (onlyOrder.getOrderNumber() == orderNumber)) {
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public void exportData() throws FlooringMasteryPersistenceException {
        throw new UnsupportedOperationException("export data Not supported yet."); // 
    }
}
