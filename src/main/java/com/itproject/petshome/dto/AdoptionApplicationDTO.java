package com.itproject.petshome.dto;

import com.itproject.petshome.model.enums.ApplicationStatus;
import com.itproject.petshome.model.enums.UserRole;
import lombok.Data;

import java.sql.Timestamp;
@Data
public class AdoptionApplicationDTO {
    private Long id;
    private Timestamp timestamp;
    private String reason;

    private ApplicationStatus applicationStatus;
    private Long userId;
    private Long petId;


}
