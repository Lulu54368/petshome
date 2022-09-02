package com.itproject.petshome.controller;

import com.itproject.petshome.dto.AdoptionApplicationDTO;
import com.itproject.petshome.dto.PetDTO;
import com.itproject.petshome.dto.PetInput;
import com.itproject.petshome.model.enums.Adopted;
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
    public List<AdoptionApplicationDTO> viewAdoptionApplications(@RequestParam Adopted adopted){
        return this.userService.viewAdoptionApplications(adopted);
    }

    @Operation(summary = "approve or reject adoption application")
    @PutMapping("/adoptionApplication")
    public AdoptionApplicationDTO updateAdoptionApplication() {
        return this.adminService.updateAdoptionApplication();
    }

}
