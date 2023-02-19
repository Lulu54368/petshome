package com.itproject.petshome.dto.output;

import lombok.Data;

@Data
public class AuthorizeOutput {

    String email;

    Long userId;

    String token;

    long expiresIn;
}
