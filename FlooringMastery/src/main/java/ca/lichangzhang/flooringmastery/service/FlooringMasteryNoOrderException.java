package ca.lichangzhang.flooringmastery.service;

/*
*
* @author catzh
* Name: Li Chang Zhang
* Email: xiaoyuzhang668@gmail.com
* Date: 2022
* 
 */
public class FlooringMasteryNoOrderException extends Exception {

    public FlooringMasteryNoOrderException(String message) {
        super(message);
    }

    public FlooringMasteryNoOrderException(String message, Throwable cause) {
        super(message, cause);
    }
}
