package ca.lichangzhang.flooringmastery.dao;

/*
*
* @author catzh
* Name: Li Chang Zhang
* Email: xiaoyuzhang668@gmail.com
* Date: 2022
* 
 */
public interface FlooringMasteryAuditDao {
    public void writeAuditEntry(String entry) throws FlooringMasteryPersistenceException;
}
