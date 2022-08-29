package com.itproject.petshome.dto;

import com.itproject.petshome.model.enums.ApplicationStatus;
import com.itproject.petshome.model.enums.UserRole;

import java.sql.Timestamp;

public class AdoptionApplicationDTO {
    private Long id;
    private Timestamp timestamp;
    private String reason;
    private String passport;
    private ApplicationStatus applicationStatus;
    private Long userId;
    private Long petId;


}
