package com.itproject.petshome.mapper;

import com.itproject.petshome.dto.AdminDTO;
import com.itproject.petshome.dto.UserDTO;
import com.itproject.petshome.model.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    AdminDTO toDto(Admin user);
}
