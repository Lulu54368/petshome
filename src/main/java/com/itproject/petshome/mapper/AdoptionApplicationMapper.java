package com.itproject.petshome.mapper;

import com.itproject.petshome.dto.AdoptionApplicationDTO;
import com.itproject.petshome.model.AdoptionApplication;
import com.itproject.petshome.repository.AdoptionApplicationRepository;
import com.itproject.petshome.repository.PetRepository;
import com.itproject.petshome.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Qualifier;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Data
@Mapper(componentModel = "spring")
public abstract class AdoptionApplicationMapper {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PetRepository petRepository;
    @Mapping(target = "user", expression = "java(userRepository.findById(adoptionApplication.getUserId()).get())")
    @Mapping(target = "pet", expression = "java(petRepository.findById(adoptionApplication.getPetId()).get())")
    public abstract AdoptionApplication toEntity(AdoptionApplicationDTO adoptionApplication);

}
