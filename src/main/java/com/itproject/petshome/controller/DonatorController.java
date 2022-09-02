package com.itproject.petshome.controller;

import com.itproject.petshome.dto.DonationDTO;
import com.itproject.petshome.dto.PetDTO;
import com.itproject.petshome.dto.PetInput;
import com.itproject.petshome.exception.UserNotFoundException;
import com.itproject.petshome.model.Donation;
import com.itproject.petshome.model.User;
import com.itproject.petshome.model.enums.Adopted;
import com.itproject.petshome.service.PetService;
import com.itproject.petshome.service.SessionService;
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
public class DonatorController {
    UserService userService;
    SessionService sessionService;
    @Operation(summary = "add donation")
    @PostMapping("/donate")
    public DonationDTO addDonation(@RequestBody DonationDTO donationDTO) {
        return this.userService.addDonation(donationDTO);
    }

    @Operation(summary = "view donation")
    @GetMapping("/viewDonation")
    public DonationDTO viewDonation() throws UserNotFoundException {
        User currUser = sessionService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        return this.userService.viewDonation(currUser);
    }
}
