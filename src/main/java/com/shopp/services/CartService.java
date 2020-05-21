package com.shopp.services;

import com.shopp.domain.CartItem;
import com.shopp.requests.CartItemRequest;
import com.shopp.requests.CartRequest;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {

    Long countCartItems(Long cartId);

    List<CartItem> getCartItems(Long cartId);

    CartItem getCartItem(Long cartId, Long bookId);

    CartItem addBookToCart(CartItemRequest cartItemRequest);

    CartItem deleteBookFromCart(CartItemRequest cartItemRequest);

    List<CartItem> checkoutBooksFromCart(CartRequest cartRequest);

    List<CartItem> purgeBooksFromCart(CartRequest cartRequest);

    BigDecimal getCartItemsSubTotal(Long cartId);
}

