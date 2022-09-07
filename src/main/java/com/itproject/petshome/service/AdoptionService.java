package com.itproject.petshome.service;

import com.itproject.petshome.dto.AdoptionApplicationDTO;
import com.itproject.petshome.dto.input.AdoptionApplicationInput;
import com.itproject.petshome.exception.AdoptionApplicationNotFound;
import com.itproject.petshome.exception.PetNotFound;
import com.itproject.petshome.exception.UserNotFoundException;
import com.itproject.petshome.mapper.AdoptionApplicationMapper;
import com.itproject.petshome.model.AdoptionApplication;
import com.itproject.petshome.model.Pet;
import com.itproject.petshome.model.User;
import com.itproject.petshome.model.enums.ApplicationStatus;
import com.itproject.petshome.repository.AdoptionApplicationCustomRepository;
import com.itproject.petshome.repository.AdoptionApplicationRepository;
import com.itproject.petshome.repository.PetRepository;
import com.itproject.petshome.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdoptionService {
    AdoptionApplicationMapper adoptionApplicationMapper;
    AdoptionApplicationRepository adoptionApplicationRepository;
    SessionService sessionService;
    PetRepository petRepository;
    UserRepository userRepository;
    AdoptionApplicationCustomRepository adoptionApplicationCustom;

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
        pet.addAdoptionApplication(adoptionApplication);
        petRepository.save(pet);
        adoptionApplicationRepository.save(adoptionApplication);
        user.addAdoptionApplication(adoptionApplication);
        userRepository.save(user);
        return adoptionApplicationMapper.toDto(adoptionApplication);

    }
    public AdoptionApplicationDTO deleteAdoptionApplication(Long petId, Long userId)
            throws PetNotFound, UserNotFoundException, AdoptionApplicationNotFound {
        Pet pet = petRepository.findById(petId).orElseThrow(PetNotFound::new);
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        AdoptionApplication adoptionApplication =
                adoptionApplicationRepository
                        .findByUserAndPet(user, pet)
                        .orElseThrow(AdoptionApplicationNotFound::new);
        adoptionApplicationRepository.delete(adoptionApplication);
        return adoptionApplicationMapper.toDto(adoptionApplication);
    }
    public List<AdoptionApplicationDTO> viewAdoptionApplications(Optional<ApplicationStatus> status,
                                                                 Optional<Long> petId) {
        Optional<Pet> pet;
        if(petId.isPresent() == false)
            pet = Optional.ofNullable(null);
        else pet = petRepository.findById(petId.get());
        return adoptionApplicationCustom.findAll(status, pet)
                .stream()
                .map(adoptionApplication -> adoptionApplicationMapper.toDto(adoptionApplication))
                .collect(Collectors.toList());


    }
}
