package com.itproject.petshome.dto.output;

import lombok.Data;

@Data
public class AdminLoginOutput {
    String username;

    Long userId;

    String token;

    long expiresIn;
}
