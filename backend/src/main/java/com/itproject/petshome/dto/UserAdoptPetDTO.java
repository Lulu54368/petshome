package com.itproject.petshome.dto;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class UserAdoptPetDTO {
    private Long id;
    private Timestamp timeAdopted;
    Long userId;
    Long petId;
}
