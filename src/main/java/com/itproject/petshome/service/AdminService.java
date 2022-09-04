package com.itproject.petshome.service;

import com.itproject.petshome.dto.AdoptionApplicationDTO;
import com.itproject.petshome.dto.input.UpdateAdoptionApplicationInput;
import com.itproject.petshome.dto.VolunteerApplicationDTO;
import com.itproject.petshome.exception.AdoptionApplicationNotFound;
import com.itproject.petshome.mapper.AdoptionApplicationMapper;
import com.itproject.petshome.model.AdoptionApplication;
import com.itproject.petshome.model.enums.ApplicationStatus;
import com.itproject.petshome.repository.AdoptionApplicationRepository;
import com.itproject.petshome.repository.VolunteerApplicationRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
@AllArgsConstructor
public class AdminService {
    private final AdoptionApplicationRepository adoptionApplicationRepository;
    private final VolunteerApplicationRepository volunteerApplicationRepository;
    public final AdoptionApplicationMapper adoptionApplicationMapper;



    public VolunteerApplicationDTO viewVolunteerApplication(ApplicationStatus status, Long id) {
        return null;
    }


    public AdoptionApplicationDTO updateAdoptionApplication
            (ApplicationStatus applicationStatus, Long applicationId) throws AdoptionApplicationNotFound {
        AdoptionApplication adoptionApplication =
                adoptionApplicationRepository.findById(applicationId)
                .orElseThrow(AdoptionApplicationNotFound:: new);
        adoptionApplication.setApplicationStatus(applicationStatus);
        return adoptionApplicationMapper.toDto(adoptionApplication);

    }
}
