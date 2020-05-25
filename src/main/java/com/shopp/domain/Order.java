package com.shopp.domain;


import com.shopp.domain.dto.OrderDTO;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "orders", schema = "public", catalog = "shoppdb")
public class Order extends AuditableEntity {

    @Column(name = "total_amount", nullable = false, length = 32)
    private BigDecimal totalAmount;

    @Column(name = "total_fees", nullable = false, length = 32)
    private BigDecimal totalFees;

    @Column(name = "net_total", nullable = false, length = 32)
    private BigDecimal netTotal;

    @Column(name = "reference", nullable = false, length = 32)
    private String reference;

    @ManyToOne
    @JoinColumn(name = "source_address_id", referencedColumnName = "id", nullable = false)
    private Address sourceAddress;

    @ManyToOne
    @JoinColumn(name = "destination_address_id", referencedColumnName = "id", nullable = false)
    private Address destinationAddress;

    @Builder.Default
    @Fetch(value = FetchMode.SUBSELECT)
    @ManyToMany(fetch = FetchType.LAZY, cascade={CascadeType.ALL}, targetEntity = CartItem.class)
    @JoinTable(name="order_cart_items",
            joinColumns = @JoinColumn(name="order_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name="cart_item_id", referencedColumnName="id"))
    private List<CartItem> cartItems = new ArrayList<>();

    @Builder.Default
    @Fetch(value = FetchMode.SUBSELECT)
    @ManyToMany(fetch = FetchType.LAZY, cascade={CascadeType.ALL}, targetEntity = Fee.class)
    @JoinTable(name="order_fees",
            joinColumns = @JoinColumn(name="order_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name="fee_id", referencedColumnName="id"))
    private List<Fee> fees = new ArrayList<>();

    @Override
    public OrderDTO toDTO() {
        return new OrderDTO(this);
    }
}
