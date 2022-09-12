package com.itproject.petshome.controller;

import com.itproject.petshome.dto.*;
import com.itproject.petshome.dto.input.PetInput;

import com.itproject.petshome.exception.PetNotFound;

import com.itproject.petshome.exception.AdoptionApplicationNotFound;
import com.itproject.petshome.model.enums.ApplicationStatus;
import com.itproject.petshome.service.AdminService;
import com.itproject.petshome.service.AdoptionService;
import com.itproject.petshome.service.PetService;
import com.itproject.petshome.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@SecurityRequirement(name = "basicAuth")
@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/api/v1/admin")
public class AdminController {
    PetService petService;
    UserService userService;
    AdminService adminService;
    AdoptionService adoptionService;
    @Operation(summary = "add pets")
    @PostMapping("/pets")
    public PetDTO addPet(@RequestBody @Valid PetInput input) {
        return this.petService.addPet(input);
    }


    @Operation(summary = "delete pets")
    @DeleteMapping("/pet/{pet_id}")
    public PetDTO deletePet(@PathVariable("pet_id") Long petId) throws PetNotFound {
        return this.petService.deletePet(petId);
    }

    @Operation(summary = "view adoption applications")
    @GetMapping("/adoption/application")
    public List<AdoptionApplicationDTO> viewAdoptionApplications(@RequestParam Optional<ApplicationStatus> status,
                                                                 @RequestParam Optional<Long> petId){
        return this.adoptionService.viewAdoptionApplications(status, petId);
    }

    @Operation(summary = "approve or reject adoption application")
    @PutMapping("/adoption/application/{id}")
    public AdoptionApplicationDTO updateAdoptionApplication
            (@RequestParam @Valid ApplicationStatus applicationstatus,
             @PathVariable("id") Long applicationId) throws AdoptionApplicationNotFound {
        return this.adminService.updateAdoptionApplication(applicationstatus, applicationId);
    }

    @Operation(summary = "view volunteer applications")
    @GetMapping("/volunteer/application/{id}")
    public VolunteerApplicationDTO viewVolunteerApplication
            (@RequestParam ApplicationStatus status,
            @PathVariable("id") Long id) {
        return this.adminService.viewVolunteerApplication(status, id);
    }

}
