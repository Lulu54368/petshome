package com.itproject.petshome.dto.input;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class VolunteerApplicationInput {
    @NotNull
    String reason;
}
