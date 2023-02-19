package com.itproject.petshome.controller;

import com.itproject.petshome.dto.*;
import com.itproject.petshome.dto.input.AdoptionApplicationInput;
import com.itproject.petshome.exception.*;
import com.itproject.petshome.model.UserAdoptPet;
import com.itproject.petshome.service.AdoptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/api/v1/adoption")
@Tag(name = "adoption")
public class AdopterController {
    AdoptionService adoptionService;

    @Operation(summary = "upload adoption application")
    @PostMapping("/application/{petId}")
    public AdoptionApplicationDTO addAdoptionApplication
            (@RequestBody @Valid AdoptionApplicationInput adoptionApplicationInput,
             @PathVariable("petId") Long petId) throws UserNotFoundException, PetNotFound,
            AdoptionApplicationAlreadyExistException, AdoptionApplicationExceedLimitException, ProfileNotUpdated {
        return this.adoptionService.addAdoptionApplication(adoptionApplicationInput, petId);
    }

    @Operation(summary = "delete adoption application for the user")
    @DeleteMapping("/application/{petId}")
    public AdoptionApplicationDTO deleteAdoptionApplication(@PathVariable("petId") Long petId) throws UserNotFoundException, PetNotFound, AdoptionApplicationNotFound {
        return adoptionService.deleteAdoptionApplication(petId);
    }
    @Operation(summary = "get the adoption application for the user")
    @GetMapping("/application")
    public List<AdoptionApplicationDTO> getAdoptionApplication() throws UserNotFoundException {
        return this.adoptionService.getAdoptionApplication();
    }

    @Operation(summary = "get the adoption for the user")
    @GetMapping("/")
    public List<UserAdoptPetDTO> addAdoption() throws UserNotFoundException {
        return this.adoptionService.getUserAdoptPet();
    }


}
