package com.shopp.domain;


import com.shopp.domain.dto.OrderDTO;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Column(name = "source_address")
    private Address sourceAddress;

    @Column(name = "destination_address")
    private Address destinationAddress;

    @ManyToMany(fetch = FetchType.LAZY, cascade={CascadeType.MERGE}, targetEntity = CartItem.class)
    @JoinTable(name="order_cart_items",
            joinColumns = @JoinColumn(name="order_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name="cart_item_id", referencedColumnName="id"))
    private List<CartItem> cartItems = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade={CascadeType.MERGE}, targetEntity = CartItem.class)
    @JoinTable(name="order_fees",
            joinColumns = @JoinColumn(name="order_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name="fee_id", referencedColumnName="id"))
    private List<Fee> fees = new ArrayList<>();

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalFees() {
        return totalFees;
    }

    public void setTotalFees(BigDecimal totalFees) {
        this.totalFees = totalFees;
    }

    public BigDecimal getNetTotal() {
        return netTotal;
    }

    public void setNetTotal(BigDecimal netTotal) {
        this.netTotal = netTotal;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public Address getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(Address sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public Address getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(Address destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public List<Fee> getFees() {
        return fees;
    }

    public void setFees(List<Fee> fees) {
        this.fees = fees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(reference, order.reference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reference);
    }

    @Override
    public OrderDTO toDTO() {
        return new OrderDTO(this);
    }
}
