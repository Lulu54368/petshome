package com.itproject.petshome.controller;

import com.itproject.petshome.dto.DonationDTO;
import com.itproject.petshome.dto.PetDTO;
import com.itproject.petshome.dto.PetInput;
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
@RequestMapping("/api/v1/donator")

public class DonatorController {
    UserService userService;
    @Operation(summary = "add donation")
    @PostMapping("/")
//    Require Front end finish the prototype and discuss what input to make
//    and if we need any services created
    public DonationDTO addDonation() {
        return null;
    }
}
