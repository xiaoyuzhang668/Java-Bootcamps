package ca.lichangzhang.vendingmachine.service;

/*
*
* @author catzh
* Name: Li Chang Zhang
* Email: xiaoyuzhang668@gmail.com
* Date: 2022
* 
 */
public class VendingMachineNoItemInventoryException extends Exception {

    public VendingMachineNoItemInventoryException(String message) {
        super(message);
    }

    public VendingMachineNoItemInventoryException(String message,
            Throwable cause) {
        super(message, cause);
    }
}
