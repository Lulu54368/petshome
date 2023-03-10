package com.itproject.petshome.mapper;

import com.itproject.petshome.dto.output.ImageOutputDTO;
import com.itproject.petshome.model.Image;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ImageMappper {

    public abstract ImageOutputDTO toDto(Image image);
}
