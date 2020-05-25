package com.shopp.domain;

import com.shopp.domain.dto.CartDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "carts", schema = "public", catalog = "shoppdb")
public class Cart extends RootEntity {

    @OneToOne
    @JoinColumn(name = "store_id", referencedColumnName = "id", nullable = false)
    private Store store;

    @Override
    public CartDTO toDTO() {
        return new CartDTO(this);
    }
}
