package com.itproject.petshome.controller;

import com.itproject.petshome.dto.VolunteerApplicationDTO;
import com.itproject.petshome.exception.UserNotFoundException;
import com.itproject.petshome.model.User;
import com.itproject.petshome.service.SessionService;
import com.itproject.petshome.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/api/v1/volunteer")
public class VolunteerController {
    private UserService userService;
    private SessionService sessionService;

    @Operation(summary = "apply to volunteer")
    @PostMapping("/applyToVolunteer")
    public VolunteerApplicationDTO addVolunteerApplication
            (@RequestBody @Valid VolunteerApplicationDTO volunteerApplicationDTO){
        return this.userService.addVolunteerApplication(volunteerApplicationDTO);
    }
}
