package com.shopp.domain;


import com.shopp.domain.dto.FeeDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name="fees", schema = "public", catalog = "shoppdb")
public class Fee extends RootEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "fee_type", nullable = false, length = 16)
    private FeeType feeType;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "description", nullable = false)
    private String description;

    public FeeType getFeeType() {
        return feeType;
    }

    public void setFeeType(FeeType feeType) {
        this.feeType = feeType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fee fee = (Fee) o;
        return Objects.equals(id, fee.id) &&
                Objects.equals(feeType, fee.feeType) &&
                Objects.equals(amount, fee.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, feeType, amount);
    }

    @Override
    public FeeDTO toDTO() {
        return null;
    }
}
