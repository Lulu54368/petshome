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
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if (user.getVerified() == null)
            return false;
        return user.getVerified();
    }
}
