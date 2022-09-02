package com.itproject.petshome.controller;

import com.itproject.petshome.dto.PetDTO;
import com.itproject.petshome.dto.PetInput;
import com.itproject.petshome.dto.UpdateUserInput;
import com.itproject.petshome.dto.UserDTO;
import com.itproject.petshome.exception.UserNotFoundException;
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
public class UserController {
    UserService userService;
    SessionService sessionService;

    @Operation(summary = "View user information")
    @GetMapping("/information")
    public UserDTO viewUserInformation() throws  UserNotFoundException {
        User currUser = sessionService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        return this.userService.viewUserInformation(currUser);
    }

    @Operation(summary = "Update or add user information")
    @PutMapping("/information")
    public UserDTO updateUserInformation(@RequestBody @Valid UpdateUserInput input) {
        return this.userService.updateUserInformation(input);
    }

}
