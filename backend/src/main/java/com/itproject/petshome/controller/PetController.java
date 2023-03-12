package com.itproject.petshome.controller;

import com.itproject.petshome.dto.PetDTO;
import com.itproject.petshome.dto.output.PetOutput;
import com.itproject.petshome.exception.PetNotFound;
import com.itproject.petshome.model.enums.*;
import com.itproject.petshome.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/api/v1/pets")
public class PetController {
    private final WebClient webClient;

    PetService petService;

    @Operation(summary = "view lost pet")
    @GetMapping("/{petId}")
    public PetDTO viewPet(@PathVariable("petId") Long petId) throws PetNotFound {

        return this.petService.viewPet(petId);
    }

    @Operation(summary = "view lost pets")
    @GetMapping(name ="/", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<PetOutput> viewPets(@RequestParam Integer page, @RequestParam Optional<Category> category,
                                    @RequestParam Optional<Adopted> adopted, @RequestParam Optional<Color> color,
                                    @RequestParam Optional<Sex> sex, @RequestParam Optional<Character> character,
                                    @RequestParam Optional<Integer> age,
                                    @RequestParam Optional<Immunization> immunization) {
        return this.petService.viewLostPet(category, adopted, color, sex, character, age, immunization, page)
                .subscribeOn(Schedulers.parallel());
    }

    public Flux<PetOutput> getResponseBody(String url, Map<String, String> params) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            uriBuilder.queryParam(entry.getKey(), entry.getValue());
        }
        URI uri = uriBuilder.build().encode().toUri();
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(PetOutput.class);
    }
}
