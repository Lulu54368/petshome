package com.itproject.petshome.dto.input;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
@Data
public class RegisterInput {

    @Email
    String email;
    @NotEmpty
    private String password;
    @NotEmpty
    private String firstname;
    @NotEmpty
    private String lastname;
}
