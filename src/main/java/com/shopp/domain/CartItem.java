package com.shopp.domain;

import com.shopp.domain.dto.CartItemDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import java.util.Objects;

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

    @Column(name = "active", nullable = false)
    private boolean active = true;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public CheckoutState getCheckoutState() {
        return checkoutState;
    }

    public void setCheckoutState(CheckoutState checkoutState) {
        this.checkoutState = checkoutState;
        switch(checkoutState){
            case QUEUED: this.setActive(true);
                break;
            case CHECKED_OUT:
            case ABANDONED:
                this.setActive(false);
                break;
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return Objects.equals(id, cartItem.id) &&
                Objects.equals(book, cartItem.book) &&
                Objects.equals(quantity, cartItem.quantity) &&
                Objects.equals(cart, cartItem.cart) &&
                Objects.equals(checkoutState, cartItem.checkoutState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, book.getId(), quantity, cart.getId(), checkoutState);
    }

    @Override
    public CartItemDTO toDTO() {
        return new CartItemDTO(this);
    }

}
