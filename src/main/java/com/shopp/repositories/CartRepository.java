package com.shopp.repositories;

import com.shopp.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("select c from Cart c where c.store.id = :storeId")
    Cart findByStoreId(@Param("storeId") Long storeId);

}
