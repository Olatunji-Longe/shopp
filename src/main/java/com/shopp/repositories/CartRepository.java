package com.shopp.repositories;

import com.shopp.domain.Cart;
import com.shopp.domain.CheckoutState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long>, JpaSpecificationExecutor<Cart> {

    List<Cart> findAllByStoreId(Long storeId);
}
