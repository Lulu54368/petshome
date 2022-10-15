package com.itproject.petshome.dto;

import com.itproject.petshome.model.ImageCollection;
import com.itproject.petshome.model.enums.*;
import com.itproject.petshome.model.enums.Color;
import io.micrometer.core.lang.Nullable;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.awt.*;
import java.util.Set;

@Data
public class PetDTO {
    @NotNull
    private Long id;
    @NotNull
    private Adopted adopted;
    @NotNull
    private Category category;
    @NotNull
    private ImageCollectionDTO imageCollectionDTO;
    @Nullable
    private  String nickname;
    private String detail;
    private City city;
    private Country country;
    @NotNull
    Set<AdoptionApplicationDTO> adoptionApplicationDTOs;
    @NotNull
    Set<UserAdoptPetDTO> userAdoptPetDTOS;

    private Color color;

    private Sex sex;

    private int age;

    private String character;

    private Immunization immunization;

}
