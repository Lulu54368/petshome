package com.itproject.petshome.controller;

import com.itproject.petshome.dto.PetDTO;
import com.itproject.petshome.dto.PetInput;
import com.itproject.petshome.service.PetService;
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
@RequestMapping("/api/v1/admin")
public class AdminController {
    PetService petService;
    @Operation(summary = "add pets")
    @PostMapping("/pets")
    public PetDTO addFood(@RequestBody @Valid PetInput input) {
        return this.petService.addPet(input);
    }
}
