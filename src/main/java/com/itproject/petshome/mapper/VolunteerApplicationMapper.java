package com.itproject.petshome.mapper;

import com.itproject.petshome.dto.DonationDTO;
import com.itproject.petshome.dto.VolunteerApplicationDTO;
import com.itproject.petshome.model.Donation;
import com.itproject.petshome.model.VolunteerApplication;
import com.itproject.petshome.repository.UserRepository;
import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
@Mapper(componentModel = "spring")
@Data
public abstract class VolunteerApplicationMapper {
    @Autowired
    UserRepository userRepository;



    @Mapping(target = "user", expression = "java(userRepository.findById( volunteerApplicationDTO.getUserId()).get())")
    public abstract VolunteerApplication toEntity(VolunteerApplicationDTO volunteerApplicationDTO);

    @Mapping(target = "userId", source = "user.id")
    public abstract VolunteerApplicationDTO toDto(VolunteerApplication volunteerApplication);
}
