package com.itproject.petshome.service;

import com.itproject.petshome.dto.AdoptionApplicationDTO;
import com.itproject.petshome.dto.UpdateAdoptionApplicationInput;
import com.itproject.petshome.dto.VolunteerApplicationDTO;
import com.itproject.petshome.model.enums.ApplicationStatus;
import com.itproject.petshome.repository.AdoptionApplicationRepository;
import com.itproject.petshome.repository.VolunteerApplicationRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
@AllArgsConstructor
public class AdminService {
    private final AdoptionApplicationRepository adoptionApplicationRepository;
    private final VolunteerApplicationRepository volunteerApplicationRepository;

    public AdoptionApplicationDTO updateAdoptionApplication
            (UpdateAdoptionApplicationInput updateAdoptionApplicationInput) {
        return null;
    }

    public VolunteerApplicationDTO viewVolunteerApplication(ApplicationStatus status, Long id) {
        return null;
    }


}
