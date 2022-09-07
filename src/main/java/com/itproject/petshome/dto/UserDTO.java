package com.itproject.petshome.dto;

import com.itproject.petshome.dto.input.UpdateUserInput;
import com.itproject.petshome.model.UserAdoptPet;
import com.itproject.petshome.model.enums.UserRole;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
public class UserDTO extends UpdateUserInput {
    @NotEmpty
    Long id;
    @Email
    String email;
    private Boolean verified;
    private Set<VolunteerApplicationDTO> volunteerApplicationDTOs;
    private Set<AdoptionApplicationDTO> adoptionApplicationDTOs;
    private Set<DonationDTO> donationDTOs;
    private Set<UserAdoptPetDTO> userAdoptPetDTOs;
    private UserRole userRole;
}
