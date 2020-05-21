package com.shopp.domain.dto;

import com.shopp.domain.User;

public class UserDTO extends EntityDTO<User> {

    private Long id;
    private String username;

    public UserDTO(User user) {
        super(user);
    }

    @Override
    protected void load(User user) {
        this.id = user.getId();
        this.username = user.getUsername();

    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
