package ca.lichangzhang.flooringmastery.dao;

/*
*
* @author catzh
* Name: Li Chang Zhang
* Email: xiaoyuzhang668@gmail.com
* Date: 2022
* 
 */
public class FlooringMasteryPersistenceException extends Exception{
  public FlooringMasteryPersistenceException(String message) {
        super(message);
    }

    public FlooringMasteryPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
