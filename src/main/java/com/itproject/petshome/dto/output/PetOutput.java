package com.itproject.petshome.dto.output;

import com.itproject.petshome.dto.AdoptionApplicationDTO;
import com.itproject.petshome.dto.ImageCollectionDTO;
import com.itproject.petshome.dto.UserAdoptPetDTO;
import com.itproject.petshome.model.Image;
import com.itproject.petshome.model.enums.*;
import io.micrometer.core.lang.Nullable;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Data
public class PetOutput {
    @NotNull
    private Long id;
    @NotNull
    private Adopted adopted;
    @NotNull
    private Category category;
    private ImageOutputDTO cover;
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
