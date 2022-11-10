package com.itproject.petshome.mapper;

import com.itproject.petshome.dto.AdoptionApplicationDTO;
import com.itproject.petshome.dto.input.AdoptionApplicationInput;
import com.itproject.petshome.dto.output.AdoptionApplicationOutput;
import com.itproject.petshome.model.AdoptionApplication;
import com.itproject.petshome.repository.PetRepository;
import com.itproject.petshome.repository.UserRepository;
import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@Mapper(componentModel = "spring")
public abstract class AdoptionApplicationMapper {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PetRepository petRepository;

    @Mapping(target = "user", expression = "java(userRepository.findById(adoptionApplicationDTO.getUserId()).get())")
    @Mapping(target = "pet", expression = "java(petRepository.findById(adoptionApplicationDTO.getPetId()).get())")
    public abstract AdoptionApplication toEntity(AdoptionApplicationDTO adoptionApplicationDTO);



    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "petId", source = "pet.id")
    public abstract AdoptionApplicationDTO toDto(AdoptionApplication adoptionApplication);
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "petId", source = "pet.id")
    @Mapping(target="lastname", source= "user.lastname")
    @Mapping(target="firstname", source="user.firstname")
    @Mapping(target="city", source="user.city")
    @Mapping(target="email", source="user.email")
    public abstract AdoptionApplicationOutput toOutput(AdoptionApplication adoptionApplication);



}
