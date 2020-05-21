package com.shopp.repositories;

import com.shopp.domain.CartItem;
import com.shopp.domain.CheckoutState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Long countAllByCartIdAndCheckoutStateAndActive(Long cartId, CheckoutState checkoutState, boolean isActive);

    CartItem findByCartIdAndBookIdAndCheckoutStateAndActive(Long cartId, Long bookId, CheckoutState checkoutState, boolean isActive);

    List<CartItem> findAllByCartIdAndCheckoutStateAndActive(Long cartId, CheckoutState checkoutState, boolean isActive);

    @Query("select sum(c.book.price * c.quantity) from CartItem c where c.cart.id = :cartId and c.checkoutState = :checkoutState and c.active = :isActive")
    Float findSubTotalByCartIdAndCheckoutStateAndActive(@Param("cartId") Long cartId, @Param("checkoutState")CheckoutState checkoutState, @Param("isActive")boolean isActive);
}
