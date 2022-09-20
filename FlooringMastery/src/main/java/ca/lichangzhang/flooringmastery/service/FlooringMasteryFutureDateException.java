package ca.lichangzhang.flooringmastery.service;

/*
*
* @author catzh
* Name: Li Chang Zhang
* Email: xiaoyuzhang668@gmail.com
* Date: 2022
* 
 */
public class FlooringMasteryFutureDateException extends Exception {
   public FlooringMasteryFutureDateException(String message) {
        super(message);
    }

    public FlooringMasteryFutureDateException (String message, Throwable cause) {
        super(message, cause);
    }
}
