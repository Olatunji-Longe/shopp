package com.shopp.requests;

public class CartItemRequest extends RequestPayload {
    private Long cartId;
    private Long bookId;
    private Integer quantity;

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean isValid(){
        if(cartId == null){
            messages.add("cartId", "value must be assigned");
        }else if(cartId < 1){
            messages.add("cartId", "must be greater than 0");
        }

        if(bookId == null){
            messages.add("bookId", "value must be assigned");
        }else if(bookId < 1){
            messages.add("bookId", "must be greater than 0");
        }

        if(quantity == null){
            quantity = 0;
        }
        return messages.toString().isEmpty();
    }

    @Override
    public String toString() {
        return "CartItemRequest{" +
                "cartId=" + cartId +
                ", bookId=" + bookId +
                ", quantity=" + quantity +
                '}';
    }
}
