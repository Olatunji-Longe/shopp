package com.shopp.domain;

import com.shopp.domain.dto.UserDTO;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "public", catalog = "shoppdb")
public class User extends RootEntity {

    @Column(name = "first_name", nullable = false, unique = true, length = 32)
    private String firstName;

    @Column(name = "last_name", nullable = false, unique = true, length = 32)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true, length = 32)
    private String email;

    @OneToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id", nullable = false)
    private Cart cart;

    @ManyToMany(fetch = FetchType.LAZY, cascade={CascadeType.MERGE}, targetEntity = CartItem.class)
    @JoinTable(name="user_addresses",
            joinColumns = @JoinColumn(name="user_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name="address_id", referencedColumnName="id"))
    private List<Address> addresses = new ArrayList<>();

    public Cart getCart() {
        return cart;
    }

    public void seCart(Cart cart) {
        this.cart = cart;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    @Override
    public UserDTO toDTO() {
        return new UserDTO(this);
    }

}
