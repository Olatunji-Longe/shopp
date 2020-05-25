package com.shopp.domain;

import com.shopp.domain.dto.CartItemDTO;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "cart_items", schema = "public", catalog = "shoppdb")
public class CartItem extends RootEntity{

    @Min(1)
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id", nullable = false)
    private Cart cart;

    @Enumerated(EnumType.STRING)
    @Column(name = "checkout_state", nullable = false, length = 16)
    private CheckoutState checkoutState;

    @Builder.Default
    @Column(name = "active", nullable = false)
    private boolean active = true;

    public void setCheckoutState(CheckoutState checkoutState) {
        this.checkoutState = checkoutState;
        switch(checkoutState){
            case QUEUED: active = true;
                break;
            case CHECKED_OUT:
            case ABANDONED:
                active = false;
                break;
        }
    }

    @Override
    public CartItemDTO toDTO() {
        return new CartItemDTO(this);
    }

    /*// Custom implementation of setter for lombok to be able to pick it up
    public static abstract class CartItemBuilder {
        public CartItemBuilder setCheckoutState(CheckoutState checkoutState) {
            this.checkoutState = checkoutState;
            switch(checkoutState){
                case QUEUED: active = true;
                    break;
                case CHECKED_OUT:
                case ABANDONED:
                    active = false;
                    break;
            }
            return this;
        }
    }*/
}
