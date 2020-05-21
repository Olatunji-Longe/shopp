package com.shopp.domain;

import com.shopp.domain.dto.UserDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "public", catalog = "shoppdb")
public class User extends RootEntity {

    @Column(name = "username", nullable = false, unique = true, length = 32)
    private String username;

    @Column(name = "password", nullable = false, length = 32)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

    @Override
    public UserDTO toDTO() {
        return new UserDTO(this);
    }

}
