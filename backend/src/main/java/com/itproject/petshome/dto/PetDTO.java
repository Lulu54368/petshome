package com.itproject.petshome.dto;

import com.itproject.petshome.model.enums.*;
import com.itproject.petshome.model.enums.Color;
import io.micrometer.core.lang.Nullable;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Data
public class PetDTO {
    @NotNull
    private Long id;
    @NotNull
    private Adopted adopted;
    @NotNull
    private Category category;
    @Nullable
    private  String nickname;
    private String detail;
    private City city;
    private Country country;
    @NotEmpty
    Set<AdoptionApplicationDTO> adoptionApplicationDTOs;
    @NotNull
    Set<UserAdoptPetDTO> userAdoptPetDTOS;

    private Color color;

    private Sex sex;

    private int age;

    private String character;

    private Immunization immunization;
    private List<CompletableFuture<byte[]>> images = new LinkedList<>();

}
