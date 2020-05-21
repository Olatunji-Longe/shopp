package com.shopp.domain;

import com.shopp.domain.dto.StoreDTO;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "stores", schema = "public", catalog = "shoppdb")
public class Store extends RootEntity {

    @Column(name = "name", nullable = false, unique = true, length = 32)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade={CascadeType.MERGE}, targetEntity = CartItem.class)
    @JoinTable(name="store_addresses",
            joinColumns = @JoinColumn(name="store_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name="address_id", referencedColumnName="id"))
    private List<Address> addresses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (!(o instanceof Store)) return false;
        Store store = (Store) o;
        return Objects.equals(id, store.id) &&
                Objects.equals(name, store.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public StoreDTO toDTO() {
        return null;
    }
}
