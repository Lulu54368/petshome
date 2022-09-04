package com.itproject.petshome.controller;

import com.itproject.petshome.dto.PetDTO;

import com.itproject.petshome.model.enums.*;
import com.itproject.petshome.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/api/v1/pet")
public class PetController {

    PetService petService;
    @Operation(summary = "view lost pets")
    @GetMapping("/")
    public List<PetDTO> viewPets(@RequestParam Optional<Category> category,
                                 @RequestParam Optional<Adopted> adopted, @RequestParam Optional<Color> color,
                                 @RequestParam Optional<Sex> sex, @RequestParam Optional<Character> character,
                                 @RequestParam Optional<Integer> age, @RequestParam Optional<Immunization> immunization) {

        return this.petService.viewLostPet(category, adopted, color, sex, character, age, immunization);
    }
}
