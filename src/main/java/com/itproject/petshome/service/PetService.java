package com.itproject.petshome.service;

import com.itproject.petshome.dto.PetDTO;
import com.itproject.petshome.dto.PetInput;
import com.itproject.petshome.mapper.PetMapper;
import com.itproject.petshome.model.Pet;
import com.itproject.petshome.repository.PetRepository;
import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.stereotype.Service;
@AllArgsConstructor
@Data
@Service
public class PetService {
    private PetRepository petRepository;
    private PetMapper petMapper;
    public PetDTO addPet(PetInput input) {
        Pet pet = petMapper.toEntity(input);
        petRepository.save(pet);
        return petMapper.toDto(pet);

    }
}
