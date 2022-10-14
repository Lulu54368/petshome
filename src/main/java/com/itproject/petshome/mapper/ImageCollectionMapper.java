package com.itproject.petshome.mapper;

import com.itproject.petshome.dto.ImageCollectionDTO;
import com.itproject.petshome.model.ImageCollection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ImageCollectionMapper {
    @Mapping(target = "petId", expression = "java(imageCollection.getPet().getId())")
    public ImageCollectionDTO toDto(ImageCollection imageCollection);
}
