package ca.lichangzhang.flooringmastery.dto;

/*
*
* @author catzh
* Name: Li Chang Zhang
* Email: xiaoyuzhang668@gmail.com
* Date: 2022
* 
 */
public class Backup {

    public int orderNumber;
    private String other;

    public Backup(int orderNumber, String other) {
        this.orderNumber = orderNumber;
        this.other = other;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
 @Override
    public String toString() {
        //display all tax header format
        String fmt = "%1s%1s%1s";    //define display format   
        return String.format(fmt, orderNumber,",",other);
    }
    
}
