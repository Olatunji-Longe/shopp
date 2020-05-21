package com.shopp.domain;

import com.shopp.domain.dto.CartDTO;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "carts", schema = "public", catalog = "shoppdb")
public class Cart extends RootEntity {

    @OneToOne
    @JoinColumn(name = "store_id", referencedColumnName = "id", nullable = false)
    private Store store;

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(id, cart.id) &&
                Objects.equals(store, cart.store);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, store.getId());
    }

    @Override
    public CartDTO toDTO() {
        return new CartDTO(this);
    }
}
