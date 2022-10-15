package com.itproject.petshome.controller;

import com.itproject.petshome.dto.input.UpdateUserInput;
import com.itproject.petshome.dto.UserDTO;
import com.itproject.petshome.exception.UserNotFoundException;
import com.itproject.petshome.model.User;
import com.itproject.petshome.service.SessionService;
import com.itproject.petshome.service.UserService;
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
@RequestMapping("/api/v1/user")
public class UserController {
    UserService userService;
    SessionService sessionService;

    @Operation(summary = "View user information")
    @GetMapping("/")
    public UserDTO viewUserInformation() throws  UserNotFoundException {

        return this.userService.viewUserInformation();
    }

    @Operation(summary = "Update or add user information")
    @PostMapping("/")
    public UserDTO updateUserInformation(@RequestBody @Valid UpdateUserInput input) throws UserNotFoundException {
        return this.userService.updateUserInformation(input);
    }
    @Operation(summary = "check whether the information is complete")
    @GetMapping("/complete")
    public Boolean checkUpdateInformation() throws  UserNotFoundException {

        return this.userService.checkUpdated();
    }

}
