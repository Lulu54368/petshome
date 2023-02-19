package com.itproject.petshome.dto.input;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
@Data
public class AdoptionApplicationInput {
    @NotNull
    String reason;

}
