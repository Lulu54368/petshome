package com.itproject.petshome.config;

import com.itproject.petshome.exception.UserNotFoundException;
import com.itproject.petshome.repository.UserRepository;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Data
@Service
public class UserDetailServices implements UserDetailsService {
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return com.itproject.petshome.model.UserDetails
                    .of(userRepository.findByEmail(username).orElseThrow(UserNotFoundException::new));
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
