package com.itproject.petshome.service;

import com.itproject.petshome.dto.PetDTO;
import com.itproject.petshome.dto.PetInput;
import com.itproject.petshome.mapper.PetMapper;
import com.itproject.petshome.model.Pet;
import com.itproject.petshome.model.enums.Adopted;
import com.itproject.petshome.repository.PetRepository;
import lombok.AllArgsConstructor;
import lombok.Data;

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
    private PetMapper petMapper;
    public PetDTO addPet(PetInput input) {
        Pet pet = petMapper.toEntity(input);
        petRepository.save(pet);
        return petMapper.toDto(pet);

    }

    public List<PetDTO> viewLostPet(Adopted adopted) {
        List<PetDTO> pets = petRepository.findAll()
                .stream()
                .filter((pet)->pet.getAdopted() == adopted)
                .map(pet->petMapper.toDto(pet))
                .collect(Collectors.toList());
        return pets;
    }




    public PetDTO updatePet(PetDTO petDTO) {
        return petDTO;
    }

    public PetDTO deletePet(PetDTO petDTO) {
        return petDTO;
    }

}
