package com.itproject.petshome.controller;

import com.itproject.petshome.dto.*;
import com.itproject.petshome.dto.input.AdoptionApplicationInput;
import com.itproject.petshome.exception.*;
import com.itproject.petshome.model.AdoptionApplication;
import com.itproject.petshome.service.AdoptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/api/v1/adoption")
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

    @Operation(summary = "delete adoption application")
    @DeleteMapping("/application/{petId}/{userId}")
    public AdoptionApplicationDTO deleteAdoptionApplication(@PathVariable("petId") Long petId,
    @PathVariable("userId") Long userId) throws UserNotFoundException, PetNotFound, AdoptionApplicationNotFound {
        return adoptionService.deleteAdoptionApplication(petId, userId);
    }


}
