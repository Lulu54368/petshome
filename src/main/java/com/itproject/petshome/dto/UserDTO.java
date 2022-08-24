package com.itproject.petshome.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
@Data
public class UserDTO extends UpdateUserInput{
    @NotEmpty
    Long id;
    @Email
    String email;
}
