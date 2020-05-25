package com.shopp.domain;

import java.math.BigDecimal;

public enum FeeType {
    TAX(true, asPercentageRatio(13)),
    SHIPPING(false, BigDecimal.ZERO),
    DISCOUNT(true, asPercentageRatio(5));

    private boolean usesPercentage;
    private BigDecimal percentage;

    FeeType(boolean usesPercentage, BigDecimal percentage) {
        this.usesPercentage = usesPercentage;
        this.percentage = percentage;
    }

    private static  BigDecimal asPercentageRatio(int percentage) {
        return BigDecimal.valueOf(percentage).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_EVEN);
    }

    public BigDecimal of(BigDecimal totalAmount) {
        return this.usesPercentage ? totalAmount.multiply(this.percentage).setScale(2, BigDecimal.ROUND_HALF_EVEN) : this.percentage;
    }

}
