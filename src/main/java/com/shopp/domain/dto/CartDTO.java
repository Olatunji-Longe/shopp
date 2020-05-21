package com.shopp.domain.dto;

import com.shopp.domain.Cart;
import com.shopp.domain.CartItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartDTO extends EntityDTO<Cart> {

    private Long id;
    private List<CartItemDTO> cartItems = new ArrayList<>();
    private BigDecimal subTotal;

    public CartDTO(Cart cart) {
        super(cart);
    }

    private CartDTO(List<CartItem> cartItems) {
        super((cartItems != null && !cartItems.isEmpty()) ? cartItems.get(0).getCart() : new Cart());
        if(cartItems != null && !cartItems.isEmpty()){
            this.cartItems = CartDTO.list(cartItems);
            this.subTotal = this.cartItems.stream().map(CartItemDTO::getTotalPrice).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        }
    }

    @Override
    protected void load(Cart cart) {
        this.id = cart.getId();
    }

    public static CartDTO of(List<CartItem> cartItems) {
        return  new CartDTO(cartItems);
    }

    public Long getId() {
        return id;
    }

    public List<CartItemDTO> getCartItems() {
        return cartItems;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

}
