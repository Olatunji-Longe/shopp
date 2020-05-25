package com.shopp.services;

import com.shopp.domain.Fee;
import com.shopp.domain.FeeType;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public interface FeeService {

    static BigDecimal calculateTotalFees(List<Fee> fees){
        return fees.stream()
                .map(Fee::getAmount)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    static  List<Fee> getRandomFees(BigDecimal totalAmount) {
        return Arrays.asList(
                Fee.builder().feeType(FeeType.SHIPPING).amount(FeeType.SHIPPING.of(totalAmount)).description("Shipping").build(),
                Fee.builder().feeType(FeeType.DISCOUNT).amount(FeeType.DISCOUNT.of(totalAmount)).description("Mother's Day Promo").build(),
                Fee.builder().feeType(FeeType.TAX).amount(FeeType.TAX.of(totalAmount)).description("Tax").build()
        );
    }

}
