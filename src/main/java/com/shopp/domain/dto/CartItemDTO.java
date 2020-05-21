package com.shopp.domain.dto;

import com.shopp.domain.CartItem;
import com.shopp.domain.CheckoutState;

import java.math.BigDecimal;

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
        this.totalPrice = cartItem.getBook().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
    }

    public Long getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BookDTO getBook() {
        return book;
    }

    public Long getCartId() {
        return cartId;
    }

    public CheckoutState getCheckoutState() {
        return checkoutState;
    }

    public boolean isActive() {
        return active;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}
