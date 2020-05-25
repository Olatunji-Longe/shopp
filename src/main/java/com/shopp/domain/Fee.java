package com.shopp.domain;


import com.shopp.domain.dto.FeeDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
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

    @Override
    public FeeDTO toDTO() {
        return new FeeDTO(this);
    }
}
