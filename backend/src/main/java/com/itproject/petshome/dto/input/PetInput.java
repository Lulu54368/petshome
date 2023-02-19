package com.itproject.petshome.dto.input;

import com.itproject.petshome.dto.output.ImageOutputDTO;
import com.itproject.petshome.model.enums.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.print.attribute.standard.ColorSupported;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class PetInput {
    @NotEmpty
    private Category category;

    @NotEmpty
    private String nickname;
    @NotNull
    private String detail;
    private List<ImageInput> images;
    private Color color = Color.UNDEFINED;

    private Sex sex = Sex.UNDEFINED;

    private int age;

    private String character;

    private Immunization immunization = Immunization.PENDING;
    private City city;
    private Country country;
}
