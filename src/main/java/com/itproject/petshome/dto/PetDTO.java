package com.itproject.petshome.dto;

import com.itproject.petshome.model.enums.Adopted;
import com.itproject.petshome.model.enums.Category;
import io.micrometer.core.lang.Nullable;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class PetDTO {
    @NotNull
    private Long id;
    @NotNull
    private Adopted adopted;
    @NotNull
    private Category category;
    @Nullable
    private byte[] picture;
    @Nullable
    private  String nickname;
    private String detail;
    @NotNull
    Set<AdoptionApplicationDTO> adoptionApplicationDTOs;
    @NotNull
    Set<UserAdoptPetDTO> userAdoptPetDTOS;

}
