package com.itproject.petshome.dto;

import com.itproject.petshome.model.enums.UserRole;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;
@Data
public class AdminDTO {
    @NotEmpty
    Long id;

    String username;
}
