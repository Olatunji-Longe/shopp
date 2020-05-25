package com.shopp.domain.dto;

import com.shopp.domain.Cart;
import com.shopp.domain.CartItem;
import com.shopp.services.CartService;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Getter
@SuperBuilder
public class CartDTO extends EntityDTO<Cart> {

    private Long id;
    private StoreDTO store;
    private List<CartItemDTO> cartItems;
    private BigDecimal subTotal;

    public CartDTO(Cart cart) {
        super(cart);
    }

    private CartDTO(List<CartItem> cartItems) {
        super((cartItems != null && !cartItems.isEmpty()) ? cartItems.get(0).getCart() : new Cart());
        if(cartItems != null && !cartItems.isEmpty()){
            this.cartItems = CartDTO.list(cartItems);
            this.subTotal = CartService.calculateTotalAmount(cartItems);
        }
    }

    @Override
    protected void load(Cart cart) {
        this.id = cart.getId();
        this.store = cart.getStore().toDTO();
    }

    public static CartDTO of(List<CartItem> cartItems) {
        return new CartDTO(cartItems);
    }

}
