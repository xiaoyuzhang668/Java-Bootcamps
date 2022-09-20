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
public class Order {

    private int orderNumber;
    private String customerName;
    private Tax tax;
    private Product product;
    private BigDecimal area;
    private BigDecimal materialCost;
    private BigDecimal laborCost;
    private BigDecimal taxSub;
    private BigDecimal total;

    //set orderNumber readonly
    public Order(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    //set orderNumber readonly
    public Order(int orderNumber, Product product, Tax tax) {
        this.orderNumber = orderNumber;
        this.product = product;
        this.tax = tax;
    }

    //set map
    public Order(int orderNumber, String customerName, Tax tax, Product product, BigDecimal area, BigDecimal materialCost, BigDecimal laborCost, BigDecimal taxSub, BigDecimal total) {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.product = product;
        this.tax = tax;
        this.area = area;
        this.materialCost = materialCost;
        this.laborCost = laborCost;
        this.taxSub = taxSub;
        this.total = total;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getArea() {
        return area.setScale(2, RoundingMode.HALF_UP);
    }

    public void setArea(BigDecimal area) {
        this.area = area.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getMaterialCost() {
        return materialCost.setScale(2, RoundingMode.HALF_UP);
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getLaborCost() {
        return laborCost.setScale(2, RoundingMode.HALF_UP);
    }

    public void setLaborCost(BigDecimal laborCost) {
        this.laborCost = laborCost.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTaxSub() {
        return taxSub.setScale(2, RoundingMode.HALF_UP);
    }

    public void setTaxSub(BigDecimal taxSub) {
        this.taxSub = taxSub.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotal() {
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    public void setTotal(BigDecimal total) {
        this.total = total.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + this.orderNumber;
        hash = 59 * hash + Objects.hashCode(this.customerName);
        hash = 59 * hash + Objects.hashCode(this.tax);
        hash = 59 * hash + Objects.hashCode(this.product);
        hash = 59 * hash + Objects.hashCode(this.area);
        hash = 59 * hash + Objects.hashCode(this.materialCost);
        hash = 59 * hash + Objects.hashCode(this.laborCost);
        hash = 59 * hash + Objects.hashCode(this.taxSub);
        hash = 59 * hash + Objects.hashCode(this.total);
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
        final Order other = (Order) obj;
        if (this.orderNumber != other.orderNumber) {
            return false;
        }
        if (!Objects.equals(this.customerName, other.customerName)) {
            return false;
        }
        if (!Objects.equals(this.tax, other.tax)) {
            return false;
        }
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        if (!Objects.equals(this.area, other.area)) {
            return false;
        }
        if (!Objects.equals(this.materialCost, other.materialCost)) {
            return false;
        }
        if (!Objects.equals(this.laborCost, other.laborCost)) {
            return false;
        }
        if (!Objects.equals(this.taxSub, other.taxSub)) {
            return false;
        }
        return Objects.equals(this.total, other.total);
    }

//    update toString to display item object
    @Override
    public String toString() {
        //display all ordder header format
        String fmt = "%1s   %1s   %1s   %1s  %1s \t%1s%1s\t %1s%1s\t %1s%1s\t %1s%1s";    //define display format 
        return String.format(fmt, orderNumber, customerName, tax, product, area, "$", materialCost, "$", laborCost, "$", taxSub, "$", total);
    }

//    @Override
//public String toString() {
//    return "Order{" + "orderNumber=" + orderNumber + ", customerName=" + customerName + ", tax=" + tax + ", product=" + product + 
//            ", area=" + area + ", materialCost=" + materialCost + ", laborCost=" + laborCost + ", taxSub=" + taxSub + ", total=" + total + '}';
//}
}
