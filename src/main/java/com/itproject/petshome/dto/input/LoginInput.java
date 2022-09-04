package com.itproject.petshome.dto.input;



import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class  LoginInput {

    @NotEmpty
    String email;

    @NotEmpty
    String password;
}