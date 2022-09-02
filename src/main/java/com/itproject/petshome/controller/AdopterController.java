package com.itproject.petshome.controller;

import com.itproject.petshome.dto.AdoptionApplicationDTO;
import com.itproject.petshome.dto.PetDTO;
import com.itproject.petshome.dto.PetInput;
import com.itproject.petshome.dto.UserAdoptPetDTO;
import com.itproject.petshome.model.AdoptionApplication;
import com.itproject.petshome.model.enums.Adopted;
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
@RequestMapping("/api/v1/user")
public class AdopterController {
    UserService userService;

    @Operation(summary = "upload adoption application")
    @PostMapping("/applyToAdopt")
    public UserAdoptPetDTO addAdoptionApplication
            (@RequestBody @Valid AdoptionApplicationDTO adoptionApplicationDTO) {
        return this.userService.addAdoptionApplication(adoptionApplicationDTO);
    }


}
