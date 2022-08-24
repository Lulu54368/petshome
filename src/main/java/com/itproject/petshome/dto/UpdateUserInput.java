package com.itproject.petshome.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UpdateUserInput {





    private String address;

    private String dob;
    @NotEmpty
    private String firstname;
    @NotEmpty
    private String lastname;

    private String bio;



}
