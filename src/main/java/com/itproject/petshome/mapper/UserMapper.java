package com.itproject.petshome.mapper;

import com.itproject.petshome.dto.UserDTO;
import com.itproject.petshome.dto.input.UpdateUserInput;
import com.itproject.petshome.model.AdoptionApplication;
import com.itproject.petshome.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {AdoptionApplicationMapper.class, UserAdoptPetMapper.class,
DonationMapper.class, VolunteerApplicationMapper.class})
public interface UserMapper {
    @Mapping(target = "verified", ignore = true)
    @Mapping(target = "adoptionApplications", ignore = true)
    @Mapping(target = "userAdoptPets", ignore = true)
    @Mapping(target = "donations", ignore = true)
    @Mapping(target = "volunteerApplications", ignore = true)
    void update(@MappingTarget User user, UpdateUserInput updateUserInput);
    @Mapping(target = "verified", expression = "java(user.getVerified())")
    @Mapping(source = "adoptionApplications", target = "adoptionApplicationDTOs")
    @Mapping(source = "userAdoptPets", target = "userAdoptPetDTOs")
    @Mapping(source = "volunteerApplications", target = "volunteerApplicationDTOs")
    @Mapping(source = "donations", target = "donationDTOs")
    UserDTO toDto(User user);


}
