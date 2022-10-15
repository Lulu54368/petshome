package com.itproject.petshome.controller;

import com.itproject.petshome.dto.VolunteerApplicationDTO;
import com.itproject.petshome.dto.input.VolunteerApplicationInput;
import com.itproject.petshome.exception.UserNotFoundException;
import com.itproject.petshome.exception.VolunteerApplicationAlreadyExistException;
import com.itproject.petshome.exception.VolunteerApplicationNotFound;
import com.itproject.petshome.model.User;
import com.itproject.petshome.service.SessionService;
import com.itproject.petshome.service.UserService;
import com.itproject.petshome.service.VolunteerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@CrossOrigin("localhost:3000")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/api/v1/volunteer")
public class VolunteerController {
    private VolunteerService volunteerService;

    @Operation(summary = "upload adoption application")
    @PostMapping("/application")
    public VolunteerApplicationDTO addVolunteerApplication
            (@RequestBody @Valid VolunteerApplicationInput volunteerApplicationInput)
            throws UserNotFoundException, VolunteerApplicationAlreadyExistException
    {
        return this.volunteerService.addVolunteerApplication(volunteerApplicationInput);
    }

    @Operation(summary = "delete volunteer application for the user")
    @DeleteMapping("/application")
    public VolunteerApplicationDTO deleteVolunteerApplication()
            throws UserNotFoundException, VolunteerApplicationNotFound
    {
        return  this.volunteerService.deleteVolunteerApplication();
    }

    @Operation(summary = "get the volunteer application for the user")
    @GetMapping("/")
    public VolunteerApplicationDTO getVolunteerApplication()
            throws UserNotFoundException, VolunteerApplicationNotFound
    {
        return this.volunteerService.getVolunteerApplication();
    }
}
