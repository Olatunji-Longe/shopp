package com.shopp.requests;

public class CartRequest extends RequestPayload {
    private Long cartId;

    public enum Action {
        checkout, reset
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    @Override
    public boolean isValid() {
        if(cartId == null){
            messages.add("cartId", "value must be assigned");
        }else if(cartId < 1){
            messages.add("cartId", "must be greater than 0");
        }

        return messages.toString().isEmpty();
    }

    @Override
    public String toString() {
        return "CartRequest{" +
                "cartId=" + cartId +
                '}';
    }
}
