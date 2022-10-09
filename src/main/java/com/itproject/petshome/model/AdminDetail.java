package com.itproject.petshome.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
@Value
@Data
@AllArgsConstructor
public class AdminDetail implements UserDetails {
    String username;
    String password;

    Long id;

    public static AdminDetail of(Admin admin) {
        return new AdminDetail(admin.getUsername(), admin.getPassword(),admin.getId());
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
        return username;
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
        return true;
    }
}
