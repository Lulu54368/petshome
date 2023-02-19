package com.itproject.petshome.dto;

import com.itproject.petshome.model.enums.ApplicationStatus;
import lombok.Data;

import java.sql.Timestamp;
@Data
public class VolunteerApplicationDTO {
    private Long id;
    private ApplicationStatus applicationStatus = ApplicationStatus.PENDING;
    private Timestamp timeApplied;
    private Timestamp timeReviewed;
    private Long userId;
}
