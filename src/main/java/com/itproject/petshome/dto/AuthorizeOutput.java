package com.itproject.petshome.dto;

import lombok.Data;

@Data
public class AuthorizeOutput {

    String email;

    Long userId;

    String token;

    long expiresIn;
}
