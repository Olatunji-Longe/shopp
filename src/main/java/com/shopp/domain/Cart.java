package com.shopp.domain;

import com.shopp.domain.dto.CartDTO;
import com.shopp.domain.dto.EntityDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "carts", schema = "public", catalog = "shoppdb")
public class Cart extends RootEntity {

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(id, cart.id) &&
                Objects.equals(user, cart.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user.getUsername());
    }

    @Override
    public CartDTO toDTO() {
        return new CartDTO(this);
    }
}
