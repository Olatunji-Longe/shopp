package com.shopp.domain;

import com.shopp.domain.dto.UserDTO;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
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

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY, cascade={CascadeType.ALL}, targetEntity = Address.class)
    @JoinTable(name="user_addresses",
            joinColumns = @JoinColumn(name="user_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name="address_id", referencedColumnName="id"))
    private List<Address> addresses = new ArrayList<>();

    @Override
    public UserDTO toDTO() {
        return new UserDTO(this);
    }

}
