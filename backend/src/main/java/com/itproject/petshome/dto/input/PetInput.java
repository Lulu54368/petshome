package com.itproject.petshome.dto.input;

import com.itproject.petshome.model.enums.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

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
    @NotNull
    private List<MultipartFile> images;
    private Color color = Color.UNDEFINED;

    private Sex sex = Sex.UNDEFINED;

    private int age;

    private String character;

    private Immunization immunization = Immunization.PENDING;
    private City city;
    private Country country;
}
