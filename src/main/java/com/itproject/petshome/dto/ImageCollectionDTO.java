package com.itproject.petshome.dto;

import com.itproject.petshome.dto.output.ImageOutputDTO;
import com.itproject.petshome.model.Image;
import com.itproject.petshome.model.Pet;
import lombok.Data;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
@Data
public class ImageCollectionDTO {

    private Long id;

    private List<ImageOutputDTO> imageList;

    private Long petId;

    private Long first;
}
