package com.shopp.domain.dto;

import com.shopp.domain.Fee;
import com.shopp.domain.FeeType;

import java.math.BigDecimal;

public class FeeDTO extends EntityDTO<Fee> {

    private Long id;
    private FeeType feeType;
    private BigDecimal amount;

    public FeeDTO(Fee fee) {
        super(fee);
    }

    @Override
    protected void load(Fee fee) {
        this.id = fee.getId();
        this.feeType = fee.getFeeType();
        this.amount = fee.getAmount();
    }

    public Long getId() {
        return id;
    }

    public FeeType getFeeType() {
        return feeType;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
