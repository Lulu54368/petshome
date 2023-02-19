package com.itproject.petshome.mapper;

import com.itproject.petshome.dto.DonationDTO;
import com.itproject.petshome.dto.UserAdoptPetDTO;
import com.itproject.petshome.model.Donation;
import com.itproject.petshome.model.UserAdoptPet;
import com.itproject.petshome.repository.UserRepository;
import lombok.Data;
import lombok.Getter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
@Data
@Mapper(componentModel = "spring")
public abstract class DonationMapper {
    @Autowired
    UserRepository userRepository;

    @Mapping(target = "user", expression = "java(userRepository.findById(donationDTO.getUserId()).get())")
    public abstract Donation toEntity(DonationDTO donationDTO);

    @Mapping(target = "userId", source = "user.id")
    public abstract DonationDTO toDto(Donation donation);



}
