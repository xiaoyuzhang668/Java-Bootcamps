package ca.lichangzhang.vendingmachine.service;

/*
*
* @author catzh
* Name: Li Chang Zhang
* Email: xiaoyuzhang668@gmail.com
* Date: 2022
* 
 */
public class VendingMachineInsufficientFundsException extends Exception {

    public VendingMachineInsufficientFundsException(String message) {
        super(message);
    }

    public VendingMachineInsufficientFundsException(String message,
            Throwable cause) {
        super(message, cause);
    }
    
}
