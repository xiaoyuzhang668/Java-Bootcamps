package ca.lichangzhang.vendingmachine.service;

/*
*
* @author catzh
* Name: Li Chang Zhang
* Email: xiaoyuzhang668@gmail.com
* Date: 2022
* 
 */
//check to see if user has deposited money, otherwise, can not select item to purchase
public class VendingMachineZeroDepositException extends Exception {

    public VendingMachineZeroDepositException(String message) {
        super(message);
    }

    public VendingMachineZeroDepositException(String message,
            Throwable cause) {
        super(message, cause);
    }
}
