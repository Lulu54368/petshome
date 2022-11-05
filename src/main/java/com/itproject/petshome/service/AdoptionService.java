package com.itproject.petshome.service;

import com.itproject.petshome.dto.AdoptionApplicationDTO;
import com.itproject.petshome.dto.UserAdoptPetDTO;
import com.itproject.petshome.dto.input.AdoptionApplicationInput;
import com.itproject.petshome.exception.*;
import com.itproject.petshome.mapper.AdoptionApplicationMapper;
import com.itproject.petshome.mapper.UserAdoptPetMapper;
import com.itproject.petshome.model.AdoptionApplication;
import com.itproject.petshome.model.Pet;
import com.itproject.petshome.model.User;
import com.itproject.petshome.model.UserAdoptPet;
import com.itproject.petshome.model.enums.ApplicationStatus;
import com.itproject.petshome.repository.*;
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

    UserAdoptPetRepository userAdoptPetRepository;

    UserAdoptPetMapper userAdoptPetMapper;

    public AdoptionApplicationDTO addAdoptionApplication
            (@Valid AdoptionApplicationInput adoptionApplicationInput, Long petId) throws UserNotFoundException,
            PetNotFound, AdoptionApplicationAlreadyExistException, AdoptionApplicationExceedLimitException,
            ProfileNotUpdated {
        Pet pet = petRepository.findById(petId).orElseThrow(PetNotFound::new);
        User user = sessionService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        if(adoptionApplicationRepository.existsByUserAndPet(user, pet) == true)
            throw new AdoptionApplicationAlreadyExistException();
        if(user.getAdoptionApplications()
                .stream()
                .filter(adoptionApplication ->
                        adoptionApplication.getApplicationStatus()
                                .equals(ApplicationStatus.PENDING))
                .collect(Collectors.toList())
                .size()>=3)
            throw new AdoptionApplicationExceedLimitException();
        if(user.getUserAdoptPets().size()>2)
            throw new AdoptionApplicationExceedLimitException();
        if(user.getIdentification() == null)
            throw new ProfileNotUpdated();
        AdoptionApplication adoptionApplication = new AdoptionApplication();
        adoptionApplication.setReason(adoptionApplicationInput.getReason());

        adoptionApplication.setTimestamp(new Timestamp(new Date().getTime()));
        adoptionApplication.setPet(pet);
        adoptionApplication.setUser(user);
       adoptionApplication =  adoptionApplicationRepository.save(adoptionApplication);

        return adoptionApplicationMapper.toDto(adoptionApplication);

    }
    public AdoptionApplicationDTO deleteAdoptionApplication(Long petId)
            throws PetNotFound, UserNotFoundException, AdoptionApplicationNotFound {
        Pet pet = petRepository.findById(petId).orElseThrow(PetNotFound::new);
        User user = sessionService.getCurrentUser().orElseThrow(UserNotFoundException::new);
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

    public List<AdoptionApplicationDTO> getAdoptionApplication() throws UserNotFoundException {
        User user = sessionService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        return adoptionApplicationRepository.findByUser(user)
                .stream()
                .map((adoptionApplication-> adoptionApplicationMapper.toDto(adoptionApplication)))
                .collect(Collectors.toList());
    }

    public List<UserAdoptPetDTO> getUserAdoptPet() throws UserNotFoundException {
        User user = sessionService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        return userAdoptPetRepository.findByUser(user)
                .stream().map(userAdoptPet -> userAdoptPetMapper.toDto(userAdoptPet))
                .collect(Collectors.toList());
    }

}
