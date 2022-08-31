package com.itproject.petshome.mapper;

import com.itproject.petshome.dto.AdoptionApplicationDTO;
import com.itproject.petshome.dto.PetDTO;
import com.itproject.petshome.dto.PetInput;
import com.itproject.petshome.model.AdoptionApplication;
import com.itproject.petshome.model.Pet;
import com.itproject.petshome.repository.AdoptionApplicationRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {AdoptionApplicationMapper.class,
UserAdoptPetMapper.class})
public interface PetMapper {

    @Mapping(target = "adoptionApplications", source = "adoptionApplicationDTOs")
    @Mapping(target = "userAdoptPets", source = "userAdoptPetDTOS")
    Pet toEntity(PetDTO pet);

    Pet toEntity(PetInput pet);
    @Mapping(source = "adoptionApplications", target = "adoptionApplicationDTOs")
    @Mapping(source = "userAdoptPets", target = "userAdoptPetDTOS")
    PetDTO toDto(Pet pet);

}
