package com.itproject.petshome.controller;

import com.itproject.petshome.config.AdminAuthenticationProvider;
import com.itproject.petshome.config.ApplicationProperties;
import com.itproject.petshome.dto.*;
import com.itproject.petshome.dto.input.AdminLogin;
import com.itproject.petshome.dto.input.LoginInput;
import com.itproject.petshome.dto.input.PetInput;

import com.itproject.petshome.dto.input.RegisterInput;
import com.itproject.petshome.dto.output.AdminLoginOutput;
import com.itproject.petshome.dto.output.AuthorizeOutput;
import com.itproject.petshome.exception.*;

import com.itproject.petshome.model.AdminDetail;
import com.itproject.petshome.model.UserDetails;
import com.itproject.petshome.model.enums.ApplicationStatus;
import com.itproject.petshome.repository.AdminRepository;
import com.itproject.petshome.service.AdminService;
import com.itproject.petshome.service.AdoptionService;
import com.itproject.petshome.service.PetService;
import com.itproject.petshome.service.UserService;
import com.itproject.petshome.utils.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/api/v1/admin")
public class AdminController {
    PetService petService;
    UserService userService;
    AdminService adminService;
    AdoptionService adoptionService;
    AdminRepository adminRepository;
    @Resource(name="adminAuthenticationProvider")
    private final AuthenticationProvider authenticationProvider;

    private final JwtTokenUtil jwtTokenUtil;

    private final ApplicationProperties properties;




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
