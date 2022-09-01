package ca.lichangzhang.vendingmachine.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
*
* @author catzh
* Name: Li Chang Zhang
* Email: xiaoyuzhang668@gmail.com
* Date: 2022
* 
 */
public class VendingMachineAuditDaoFileImpl implements VendingMachineAuditDao {
    
    private final String AUDIT_FILE;

       public VendingMachineAuditDaoFileImpl() {
        AUDIT_FILE = "audit.txt";
    }

    //different constructor
    public VendingMachineAuditDaoFileImpl(String testAuditFile) {
        AUDIT_FILE = testAuditFile;
    }
    
    @Override
    public void writeAudit(String entry) throws VendingMachinePersistenceException {
        PrintWriter out;
        
        try {
            out = new PrintWriter(new FileWriter(AUDIT_FILE,true));
        } catch (IOException e){
            throw new VendingMachinePersistenceException (
            " Could not persist audit information.", e);
        }
        
        LocalDateTime timestamp = LocalDateTime.now();
        String formatted = timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        out.println( formatted + " : " + entry);
        out.flush();    
    }
}
