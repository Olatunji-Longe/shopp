package com.shopp.domain.dto;

import com.shopp.domain.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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


}
