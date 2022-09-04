package com.itproject.petshome.service;

import com.itproject.petshome.dto.PetDTO;
import com.itproject.petshome.dto.PetInput;
import com.itproject.petshome.mapper.PetMapper;
import com.itproject.petshome.model.Pet;
import com.itproject.petshome.model.enums.*;
import com.itproject.petshome.repository.PetRepository;
import com.itproject.petshome.repository.PetRepositoryCustom;
import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
@Service
public class PetService {
    private PetRepository petRepository;
    private PetRepositoryCustom petRepositoryCustom;

    private PetMapper petMapper;
    public PetDTO addPet(PetInput input) {
        Pet pet = petMapper.toEntity(input);
        petRepository.save(pet);
        return petMapper.toDto(pet);

    }






    public PetDTO updatePet(PetDTO petDTO) {
        return petDTO;
    }

    public PetDTO deletePet(PetDTO petDTO) {
        return petDTO;
    }

    public List<PetDTO> viewLostPet(Optional<Category> category,
                                    Optional<Adopted> adopted, Optional<Color> color,
                                    Optional<Sex> sex,
                                    Optional<Character> character,
                                    Optional<Integer> age, Optional<Immunization> immunization) {
        return petRepositoryCustom
                .findByParameters(category, adopted, color, sex, character, age, immunization)
                .stream()
                .map(pet->petMapper.toDto(pet))
                .collect(Collectors.toList());

    }
}
