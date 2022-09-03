package com.itproject.petshome.dto;

import com.itproject.petshome.model.enums.ApplicationStatus;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UpdateAdoptionApplicationInput {
    @NotEmpty
    private ApplicationStatus applicationStatus;
    @NotEmpty
    private Long volunteerApplicationId;

}
