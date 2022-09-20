package ca.lichangzhang.vendingmachine.dao;

/*
*
* @author catzh
* Name: Li Chang Zhang
* Email: xiaoyuzhang668@gmail.com
* Date: 2022
* 
 */
public interface VendingMachineAuditDao {
public void writeAudit(String entry) throws
        VendingMachinePersistenceException; 
}
