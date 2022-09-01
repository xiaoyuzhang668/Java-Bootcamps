package ca.lichangzhang.vendingmachine.dto;

import java.math.BigDecimal;

/*
*
* @author catzh
* Name: Li Chang Zhang
* Email: xiaoyuzhang668@gmail.com
* Date: 2022
* 
 */
public class Change {

    public int numQuarter;
    public int numDime;
    public int numNickel;
    public int numPenny;

    public BigDecimal balance;

    public int getNumQuarter() {
        return numQuarter;
    }

    public void setNumQuarter(int numQuarter) {
        this.numQuarter = numQuarter;
    }

    public int getNumDime() {
        return numDime;
    }

    public void setNumDime(int numDime) {
        this.numDime = numDime;
    }

    public int getNumNickel() {
        return numNickel;
    }

    public void setNumNickel(int numNickel) {
        this.numNickel = numNickel;
    }

    public int getNumPenny() {
        return numPenny;
    }

    public void setNumPenny(int numPenny) {
        this.numPenny = numPenny;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}
