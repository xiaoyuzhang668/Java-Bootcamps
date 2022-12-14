package ca.lichangzhang.flooringmastery.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/*
*
* @author catzh
* Name: Li Chang Zhang
* Email: xiaoyuzhang668@gmail.com
* Date: 2022
* 
 */
public class Tax {

    private String stateAbbreviation;
    private String stateName;
    private BigDecimal taxRate;

//set stateAbbreviation read only
    public Tax(String stateAbbreviation) {
        this.stateAbbreviation = stateAbbreviation;
    }

    public Tax(String stateAbbreviation, String stateName, BigDecimal taxRate) {
        this.stateAbbreviation = stateAbbreviation;
        this.stateName = stateName;
        this.taxRate = taxRate.setScale(2, RoundingMode.HALF_UP);
    }

    public String getStateAbbreviation() {
        return stateAbbreviation;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public BigDecimal getTaxRate() {
        return taxRate.setScale(2, RoundingMode.HALF_UP);
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.stateAbbreviation);
        hash = 71 * hash + Objects.hashCode(this.stateName);
        hash = 71 * hash + Objects.hashCode(this.taxRate);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tax other = (Tax) obj;
        if (!Objects.equals(this.stateAbbreviation, other.stateAbbreviation)) {
            return false;
        }
        if (!Objects.equals(this.stateName, other.stateName)) {
            return false;
        }
        return Objects.equals(this.taxRate, other.taxRate);
    }

    @Override
    public String toString() {
        //display all tax header format
        String fmt = "%3s    %8s";    //define display format   
        return String.format(fmt, stateAbbreviation, taxRate);
    }
//    @Override
//    public String toString() {
//        return "Tax{" + "stateAbbreviation=" + stateAbbreviation + ", stateName=" + stateName + ", taxRate=" + taxRate + '}';
//    }
}
