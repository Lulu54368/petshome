package com.itproject.petshome.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
@Value
@Data
@AllArgsConstructor
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails{

    String email;
    String password;

    Long id;
    User user;

    public static UserDetails of(User user) {
        return new UserDetails(user.getEmail(), user.getPassword(),user.getId(), user);
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
