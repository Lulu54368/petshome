package com.itproject.petshome.dto.input;

import com.itproject.petshome.model.enums.Category;
import com.itproject.petshome.model.enums.Immunization;
import com.itproject.petshome.model.enums.Sex;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.print.attribute.standard.ColorSupported;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.itproject.petshome.model.enums.Color;
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
    private Color color = Color.UNDEFINED;

    private Sex sex = Sex.UNDEFINED;

    private int age;

    private String character;

    private Immunization immunization = Immunization.PENDING;
}
