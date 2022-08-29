package com.itproject.petshome.dto;

import com.itproject.petshome.model.enums.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class PetInput {
    @NotEmpty
    private Category category;
    @Schema(nullable = true, description = "The base64 of the picture, can be null")
    private byte[] picture;
    @NotEmpty
    private String nickname;
    @NotNull
    private String detail;
}
