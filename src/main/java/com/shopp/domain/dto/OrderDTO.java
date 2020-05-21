package com.shopp.domain.dto;

import com.shopp.domain.Order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDTO extends EntityDTO<Order> {

    private Long id;
    private BigDecimal totalAmount;
    private BigDecimal totalFees;
    private BigDecimal netTotal;
    private String reference;
    private AddressDTO sourceAddress;
    private AddressDTO destinationAddress;
    private List<CartItemDTO> cartItems = new ArrayList<>();
    private List<FeeDTO> fees = new ArrayList<>();
    private String createdBy;
    private Date createdAt;
    private String modifiedBy;
    private Date modifiedAt;

    public OrderDTO(Order entity) {
        super(entity);
    }

    @Override
    protected void load(Order order) {
        this.id = order.getId();
        this.reference = order.getReference();
        this.sourceAddress = order.getSourceAddress().toDTO();
        this.destinationAddress = order.getDestinationAddress().toDTO();
        this.cartItems = CartItemDTO.list(order.getCartItems());
        this.fees = FeeDTO.list(order.getFees());
        this.createdBy = order.getCreatedBy();
        this.createdAt = order.getCreatedAt();
        this.modifiedBy = order.getModifiedBy();
        this.modifiedAt = order.getModifiedAt();
        this.totalAmount = order.getTotalAmount();
        this.totalFees = order.getTotalFees();
        this.netTotal = order.getNetTotal();
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public BigDecimal getTotalFees() {
        return totalFees;
    }

    public BigDecimal getNetTotal() {
        return netTotal;
    }

    public String getReference() {
        return reference;
    }

    public List<FeeDTO> getFees() {
        return fees;
    }

    public AddressDTO getSourceAddress() {
        return sourceAddress;
    }

    public AddressDTO getDestinationAddress() {
        return destinationAddress;
    }

    public List<CartItemDTO> getCartItems() {
        return cartItems;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

}
