package com.shopp.domain.dto;

import com.shopp.domain.Fee;
import com.shopp.domain.FeeType;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@SuperBuilder
public class FeeDTO extends EntityDTO<Fee> {

    private Long id;
    private FeeType feeType;
    private String description;
    private BigDecimal amount;

    public FeeDTO(Fee fee) {
        super(fee);
    }

    @Override
    protected void load(Fee fee) {
        this.id = fee.getId();
        this.feeType = fee.getFeeType();
        this.description = fee.getDescription();
        this.amount = fee.getAmount();
    }

}
