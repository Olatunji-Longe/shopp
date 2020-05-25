package com.shopp.domain.dto;

import com.shopp.domain.Credential;
import com.shopp.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@SuperBuilder
public class UserDTO extends EntityDTO<User> {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private List<AddressDTO> addresses;
    private CartDTO cart;

    public UserDTO(User user) {
        super(user);
    }

    private UserDTO(Credential credential){
        super(credential.getUser());
        this.username = credential.getUsername();
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

    public static UserDTO of(Credential credential){
        return new UserDTO(credential);
    }

}
