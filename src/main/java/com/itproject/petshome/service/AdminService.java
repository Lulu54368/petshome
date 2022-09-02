package com.itproject.petshome.service;

import com.itproject.petshome.dto.AdoptionApplicationDTO;
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
            (AdoptionApplicationDTO adoptionApplicationDTO, Long adoptionApplicationId) {
        return null;
    }

    public List<VolunteerApplicationDTO> viewVolunteerApplications(ApplicationStatus status) {
        return null;
    }
}
