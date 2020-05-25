package com.shopp.domain;

import com.shopp.domain.dto.StoreDTO;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "stores", schema = "public", catalog = "shoppdb")
public class Store extends RootEntity {

    @Column(name = "name", nullable = false, unique = true, length = 32)
    private String name;

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY, cascade={CascadeType.ALL}, targetEntity = Address.class)
    @JoinTable(name="store_addresses",
            joinColumns = @JoinColumn(name="store_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name="address_id", referencedColumnName="id"))
    private List<Address> addresses = new ArrayList<>();

    @Override
    public StoreDTO toDTO() {
        return new StoreDTO(this);
    }
}
