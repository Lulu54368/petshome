package com.itproject.petshome.mapper;

import com.itproject.petshome.dto.PetDTO;
import com.itproject.petshome.dto.input.PetInput;
import com.itproject.petshome.dto.output.PetOutput;
import com.itproject.petshome.model.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AdoptionApplicationMapper.class,
UserAdoptPetMapper.class})
public interface PetMapper {

    @Mapping(target = "adoptionApplications", source = "adoptionApplicationDTOs")
    @Mapping(target = "userAdoptPets", source = "userAdoptPetDTOS")
    Pet toEntity(PetDTO pet);

    Pet toEntity(PetInput pet);
    @Mapping(target = "images", ignore = true)
    @Mapping(source = "adoptionApplications", target = "adoptionApplicationDTOs")
    @Mapping(source = "userAdoptPets", target = "userAdoptPetDTOS")
    PetDTO toDto(Pet pet);
    @Mapping(source = "adoptionApplications", target = "adoptionApplicationDTOs")
    @Mapping(source = "userAdoptPets", target = "userAdoptPetDTOS")
    @Mapping(target = "cover", ignore = true)
    PetOutput toOutput(Pet pet);


}
