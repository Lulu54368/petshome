package com.itproject.petshome.mapper;

import com.itproject.petshome.dto.output.ImageOutputDTO;
import com.itproject.petshome.model.Image;
import com.itproject.petshome.repository.ImageCollectionRepository;
import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ImageMappper {
    @Mapping(target = "imageCollectionId", expression = "java(image.getImageCollection().getId())")
    public abstract ImageOutputDTO toDto(Image image);
}
