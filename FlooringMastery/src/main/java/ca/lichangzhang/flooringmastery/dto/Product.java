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
public class Product {

    private String productType;
    private BigDecimal costPerSquareFoot;
    private BigDecimal laborCostPerSquareFoot;

//set productType readonly 
    public Product(String productType) {
        this.productType = productType;
    }

    public Product(String productType, BigDecimal costPerSquareFoot, BigDecimal laborCostPerSquareFoot) {
        this.productType = productType;
        this.costPerSquareFoot = costPerSquareFoot.setScale(2, RoundingMode.HALF_UP);
        this.laborCostPerSquareFoot = laborCostPerSquareFoot.setScale(2, RoundingMode.HALF_UP);
    }

    public String getProductType() {
        return productType;
    }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot.setScale(2, RoundingMode.HALF_UP);
    }

    public void setCostPerSquareFoot(BigDecimal CostPerSquareFoot) {
        this.costPerSquareFoot = CostPerSquareFoot.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot.setScale(2, RoundingMode.HALF_UP);
    }

    public void setLaborCostPerSquareFoot(BigDecimal LaborCostPerSquareFoot) {
        this.laborCostPerSquareFoot = LaborCostPerSquareFoot.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.productType);
        hash = 17 * hash + Objects.hashCode(this.costPerSquareFoot);
        hash = 17 * hash + Objects.hashCode(this.laborCostPerSquareFoot);
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
        final Product other = (Product) obj;
        if (!Objects.equals(this.productType, other.productType)) {
            return false;
        }
        if (!Objects.equals(this.costPerSquareFoot, other.costPerSquareFoot)) {
            return false;
        }
        return Objects.equals(this.laborCostPerSquareFoot, other.laborCostPerSquareFoot);
    }

    @Override
    public String toString() {
        //display all tax header format
        String fmt = "%-8s\t%1s%1s    %1s%s";    //define display format   
        return String.format(fmt, productType, "$", costPerSquareFoot, "$", laborCostPerSquareFoot);
    }
//    @Override
//    public String toString() {
//        return "Product{" + "productType=" + productType
//                + ", materialCost=" + costPerSquareFoot + ", laborCost=" + laborCostPerSquareFoot + '}';
//    }
}
