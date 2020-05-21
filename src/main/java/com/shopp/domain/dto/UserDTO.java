package com.shopp.domain.dto;

import com.shopp.domain.User;

import java.util.List;

public class UserDTO extends EntityDTO<User> {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<AddressDTO> addresses;
    private CartDTO cart;

    public UserDTO(User user) {
        super(user);
    }

    @Override
    protected void load(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.addresses = AddressDTO.list(user.getAddresses());
        this.cart = user.getCart().toDTO();

    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public List<AddressDTO> getAddresses() {
        return addresses;
    }

    public CartDTO getCart() {
        return cart;
    }
}
