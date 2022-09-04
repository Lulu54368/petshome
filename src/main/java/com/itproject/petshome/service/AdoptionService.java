package com.itproject.petshome.service;

import com.itproject.petshome.dto.AdoptionApplicationDTO;
import com.itproject.petshome.dto.input.AdoptionApplicationInput;
import com.itproject.petshome.exception.PetNotFound;
import com.itproject.petshome.exception.UserNotFoundException;
import com.itproject.petshome.mapper.AdoptionApplicationMapper;
import com.itproject.petshome.model.AdoptionApplication;
import com.itproject.petshome.model.Pet;
import com.itproject.petshome.model.User;
import com.itproject.petshome.model.enums.ApplicationStatus;
import com.itproject.petshome.repository.AdoptionApplicationRepository;
import com.itproject.petshome.repository.PetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
@Service
@AllArgsConstructor
public class AdoptionService {
    AdoptionApplicationMapper adoptionApplicationMapper;
    AdoptionApplicationRepository adoptionApplicationRepository;
    SessionService sessionService;
    PetRepository petRepository;
    public AdoptionApplicationDTO addAdoptionApplication
            (@Valid AdoptionApplicationInput adoptionApplicationInput, Long petId) throws UserNotFoundException, PetNotFound {
        Pet pet = petRepository.findById(petId).orElseThrow(PetNotFound::new);
        User user = sessionService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        AdoptionApplication adoptionApplication = new AdoptionApplication();
        adoptionApplication.setReason(adoptionApplicationInput.getReason());
        adoptionApplication.setPassport(adoptionApplicationInput.getPassport());
        adoptionApplication.setTimestamp(new Timestamp(new Date().getTime()));
        adoptionApplication.setPet(pet);
        adoptionApplication.setUser(user);
        adoptionApplicationRepository.save(adoptionApplication);
        return adoptionApplicationMapper.toDto(adoptionApplication);

    }
    public List<AdoptionApplicationDTO> viewAdoptionApplications(ApplicationStatus status) {
        return null;
    }
}
