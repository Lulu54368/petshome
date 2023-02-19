package com.itproject.petshome.dto.input;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AdminLogin {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
