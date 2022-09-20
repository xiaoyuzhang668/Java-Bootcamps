package ca.lichangzhang.vendingmachine.dto;

import java.math.BigDecimal;

/**
 *
 * @author catzh Name: Li Chang Zhang Email: xiaoyuzhang668@gmail.com Date: 2022
 *
 */
public enum Coin {  //enum class for currency coin and set int parameters.
    QUARTERS(BigDecimal.valueOf(0.25)),
    DIMES(BigDecimal.valueOf(0.10)),
    NICKELS(BigDecimal.valueOf(0.05)),
    PENNY(BigDecimal.valueOf(0.01));

    private final BigDecimal value;

    private Coin(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() { // set accessors - setter
        return value;
    }
}
