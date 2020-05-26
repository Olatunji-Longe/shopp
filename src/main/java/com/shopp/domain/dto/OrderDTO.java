package com.shopp.domain.dto;

import com.shopp.domain.Order;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Getter
@SuperBuilder
public class OrderDTO extends EntityDTO<Order> {

    private Long id;
    private BigDecimal totalAmount;
    private BigDecimal totalFees;
    private BigDecimal netTotal;
    private String reference;
    private AddressDTO sourceAddress;
    private AddressDTO destinationAddress;
    private List<CartItemDTO> cartItems;
    private List<FeeDTO> fees;

    private String createdBy;
    private Long createdAtMillis;
    private String modifiedBy;
    private Long modifiedAtMillis;

    public OrderDTO(Order entity) {
        super(entity);
    }

    @Override
    protected void load(Order order) {
        this.id = order.getId();
        this.reference = order.getReference();
        this.sourceAddress = order.getSourceAddress() != null ? order.getSourceAddress().toDTO() : null;
        this.destinationAddress = order.getDestinationAddress() != null ? order.getDestinationAddress().toDTO() : null;
        this.cartItems = CartItemDTO.list(order.getCartItems());
        this.fees = FeeDTO.list(order.getFees());
        this.createdBy = order.getCreatedBy();
        this.createdAtMillis = order.getCreatedAt().getTime();
        this.modifiedBy = order.getModifiedBy();
        this.modifiedAtMillis = order.getModifiedAt().getTime();
        this.totalAmount = order.getTotalAmount();
        this.totalFees = order.getTotalFees();
        this.netTotal = order.getNetTotal();
    }


}
