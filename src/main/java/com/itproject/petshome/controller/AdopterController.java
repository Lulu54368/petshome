package com.itproject.petshome.controller;

import com.itproject.petshome.dto.*;
import com.itproject.petshome.dto.input.AdoptionApplicationInput;
import com.itproject.petshome.exception.PetNotFound;
import com.itproject.petshome.exception.UserNotFoundException;
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
@RequestMapping("/api/v1/adopter")
public class AdopterController {
    AdoptionService adoptionService;

    @Operation(summary = "upload adoption application")
    @PostMapping("/applyToAdopt/{petId}")
    public AdoptionApplicationDTO addAdoptionApplication
            (@RequestBody @Valid AdoptionApplicationInput adoptionApplicationInput,
             @PathVariable("petId") Long petId) throws UserNotFoundException, PetNotFound {
        return this.adoptionService.addAdoptionApplication(adoptionApplicationInput, petId);
    }


}
