package com.itproject.petshome.controller;

import com.itproject.petshome.dto.DonationDTO;

import com.itproject.petshome.dto.PetDTO;

import com.itproject.petshome.dto.input.DonationInput;
import com.itproject.petshome.exception.UserNotFoundException;
import com.itproject.petshome.model.Donation;
import com.itproject.petshome.model.User;
import com.itproject.petshome.model.enums.Adopted;
import com.itproject.petshome.service.DonatorService;
import com.itproject.petshome.service.PetService;
import com.itproject.petshome.service.SessionService;
import com.itproject.petshome.service.UserService;
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
@RequestMapping("/api/v1/donation")
@Tag(name = "donation")
public class DonatorController {

    DonatorService donatorService;
    @Operation(summary = "add donation")
    @PostMapping("/")
    public DonationDTO addDonation(@RequestBody @Valid DonationInput donationInput) throws UserNotFoundException{
        return this.donatorService.addDonation(donationInput);
    }

    @Operation(summary = "view donation")
    @GetMapping("/")
    public List<DonationDTO> viewDonation() throws UserNotFoundException {
        return this.donatorService.viewDonation();
    }
}
