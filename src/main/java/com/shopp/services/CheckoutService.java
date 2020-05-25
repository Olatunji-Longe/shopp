package com.shopp.services;

import com.shopp.domain.Cart;
import com.shopp.domain.Order;

public interface CheckoutService {
    Order checkoutOrder(Cart cart);
}
