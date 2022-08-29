package com.itproject.petshome.dto;

import com.itproject.petshome.model.AdoptionApplication;
import com.itproject.petshome.model.Donation;
import com.itproject.petshome.model.UserAdoptPet;
import com.itproject.petshome.model.enums.UserRole;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
public class UserDTO extends UpdateUserInput{
    @NotEmpty
    Long id;
    @Email
    String email;
    private Boolean verified;
    private Set<VolunteerApplicationDTO> volunteerApplicationDTOS;
    private Set<AdoptionApplicationDTO> adoptionApplicationDTOS;
    private Set<DonationDTO> donationDTOS;
    private Set<UserAdoptPet> userAdoptPets;
    private UserRole userRole;
}
