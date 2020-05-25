package com.shopp.repositories.specifications;

import com.shopp.domain.Cart;
import com.shopp.domain.CheckoutState;
import org.springframework.data.jpa.domain.Specification;

public final class CartSpecification {

    private CartSpecification() {}

    public static Specification<Cart> whereBookIdCheckoutStateAndActive(Long cartId, Long bookId, CheckoutState checkoutState, boolean isActive) {
        return (root, query, cb) -> cb.and(
                cb.equal(root.get("id"), cartId),
                cb.equal(root.join("cartItems").join("book").get("id"), bookId),
                cb.equal(root.join("cartItems").get("checkoutState"), checkoutState),
                cb.equal(root.join("cartItems").get("active"), isActive)
            );
    }
}
