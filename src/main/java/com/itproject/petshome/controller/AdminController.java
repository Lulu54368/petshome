package com.itproject.petshome.controller;

import com.itproject.petshome.dto.*;
import com.itproject.petshome.model.enums.Adopted;
import com.itproject.petshome.model.enums.ApplicationStatus;
import com.itproject.petshome.service.AdminService;
import com.itproject.petshome.service.PetService;
import com.itproject.petshome.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/api/v1/admin")
public class AdminController {
    PetService petService;
    UserService userService;
    AdminService adminService;
    @Operation(summary = "add pets")
    @PostMapping("/pets")
    public PetDTO addPet(@RequestBody @Valid PetInput input) {
        return this.petService.addPet(input);
    }

    @Operation(summary = "update pets")
    @PutMapping("/pets")
    public PetDTO updatePet(PetDTO petDTO) {
        return this.petService.updatePet(petDTO);
    }

    @Operation(summary = "delete pets")
    @DeleteMapping("/pets")
    public PetDTO deletePet(PetDTO petDTO) {
        return this.petService.deletePet(petDTO);
    }

    @Operation(summary = "view adoption applications")
    @GetMapping("/adoptionApplication")
    public List<AdoptionApplicationDTO> viewAdoptionApplications(@RequestParam ApplicationStatus status){
        return this.userService.viewAdoptionApplications(status);
    }

    @Operation(summary = "approve or reject adoption application")
    @PutMapping("/adoptionApplication/{id}")
    public AdoptionApplicationDTO updateAdoptionApplication
            (@RequestBody @Valid UpdateAdoptionApplicationInput updateAdoptionApplicationInput) {
        return this.adminService.updateAdoptionApplication(updateAdoptionApplicationInput);
    }

    @Operation(summary = "view volunteer applications")
    @GetMapping("/volunteerApplication/{id}")
    public VolunteerApplicationDTO viewVolunteerApplication
            (@RequestParam ApplicationStatus status,
            @PathVariable("id") Long id) {
        return this.adminService.viewVolunteerApplication(status, id);
    }

}
