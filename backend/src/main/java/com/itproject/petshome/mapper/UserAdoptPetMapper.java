package com.itproject.petshome.mapper;

import com.itproject.petshome.dto.UserAdoptPetDTO;
import com.itproject.petshome.model.UserAdoptPet;
import com.itproject.petshome.repository.PetRepository;
import com.itproject.petshome.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@Mapper(componentModel= "spring")
public abstract class UserAdoptPetMapper  {
    @Autowired
    PetRepository petRepository;
    @Autowired
    UserRepository userRepository;
    @Mapping(target = "user", expression = "java(userRepository.findById(userAdoptPetDTO.getUserId()).get())")
    @Mapping(target = "pet", expression = "java(petRepository.findById(userAdoptPetDTO.getPetId()).get())")
    public abstract UserAdoptPet toEntity(UserAdoptPetDTO userAdoptPetDTO);
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "petId", source = "pet.id")
    public abstract UserAdoptPetDTO toDto(UserAdoptPet userAdoptPet);

}
