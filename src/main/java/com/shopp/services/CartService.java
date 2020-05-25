package com.shopp.services;

import com.shopp.domain.CartItem;
import com.shopp.domain.Order;
import com.shopp.requests.CartItemRequest;
import com.shopp.requests.CartRequest;
import com.shopp.utils.CacheKeyGen;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {

    Long countCartItems(Long cartId);

    List<CartItem> getCartItems(Long cartId);

    CartItem getCartItem(Long cartId, Long bookId);

    CartItem addBookToCart(CartItemRequest cartItemRequest);

    CartItem deleteBookFromCart(CartItemRequest cartItemRequest);

    Order checkoutBooksFromCart(CartRequest cartRequest);

    List<CartItem> purgeBooksFromCart(CartRequest cartRequest);

    BigDecimal getCartItemsSubTotal(Long cartId);

    static String getCacheKey(Long cartId){
        return CacheKeyGen.key(String.format("cached-cart-%s", cartId));
    }

    static String getCacheKey(Long cartId, Long bookId){
        return CacheKeyGen.key(String.format("cached-cart-%s-book-%s", cartId, bookId));
    }

    static BigDecimal calculateCartItemTotalPrice(CartItem cartItem){
        return cartItem.getBook().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
    }

    static BigDecimal calculateTotalAmount(List<CartItem> cartItems){
        return cartItems.stream()
                .map(CartService::calculateCartItemTotalPrice)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

}

