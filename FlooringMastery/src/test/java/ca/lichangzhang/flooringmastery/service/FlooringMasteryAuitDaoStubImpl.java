package ca.lichangzhang.flooringmastery.service;

import ca.lichangzhang.flooringmastery.dao.FlooringMasteryAuditDao;
import ca.lichangzhang.flooringmastery.dao.FlooringMasteryPersistenceException;

/*
*
* @author catzh
* Name: Li Chang Zhang
* Email: xiaoyuzhang668@gmail.com
* Date: 2022
* 
 */
public class FlooringMasteryAuitDaoStubImpl implements FlooringMasteryAuditDao {
  @Override
    public void writeAuditEntry(String entry) throws FlooringMasteryPersistenceException {
        //do nothing . . .
    }
}
