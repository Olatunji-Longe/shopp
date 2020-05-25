package com.shopp.domain.dto;

import com.shopp.domain.CartItem;
import com.shopp.domain.CheckoutState;
import com.shopp.services.CartService;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@SuperBuilder
public class CartItemDTO extends EntityDTO<CartItem> {

    private Long id;
    private Integer quantity;
    private BookDTO book;
    private Long cartId;
    private CheckoutState checkoutState;
    private boolean active;
    private BigDecimal totalPrice;

    public CartItemDTO(CartItem cartItem) {
        super(cartItem);
    }

    @Override
    protected void load(CartItem cartItem) {
        this.id = cartItem.getId();
        this.quantity = cartItem.getQuantity();
        this.book = cartItem.getBook() != null ? cartItem.getBook().toDTO() : null;
        this.cartId = cartItem.getCart() != null ? cartItem.getCart().getId() : null;
        this.checkoutState = cartItem.getCheckoutState();
        this.active = cartItem.isActive();
        this.totalPrice = CartService.calculateCartItemTotalPrice(cartItem);
    }

}
