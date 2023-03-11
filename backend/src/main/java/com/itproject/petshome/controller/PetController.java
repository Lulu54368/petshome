package com.itproject.petshome.controller;

import com.itproject.petshome.dto.PetDTO;
import com.itproject.petshome.dto.output.PetOutput;
import com.itproject.petshome.exception.PetNotFound;
import com.itproject.petshome.model.enums.*;
import com.itproject.petshome.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/api/v1/pets")
public class PetController {

    PetService petService;

    @Operation(summary = "view lost pet")
    @GetMapping("/{petId}")
    public PetDTO viewPet(@PathVariable("petId") Long petId) throws PetNotFound {

        return this.petService.viewPet(petId);
    }
    @Operation(summary = "view lost pets")
    @GetMapping("/")
    public Flux<PetOutput> viewPets(@RequestParam Integer page, @RequestParam Optional<Category> category,
                                    @RequestParam Optional<Adopted> adopted, @RequestParam Optional<Color> color,
                                    @RequestParam Optional<Sex> sex, @RequestParam Optional<Character> character,
                                    @RequestParam Optional<Integer> age,
                                    @RequestParam Optional<Immunization> immunization) {

        return this.petService.viewLostPet(category, adopted, color, sex, character, age, immunization, page);
    }
}
