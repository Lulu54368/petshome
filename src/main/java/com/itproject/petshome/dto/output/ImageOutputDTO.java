package com.itproject.petshome.dto.output;

import lombok.Data;

@Data
public class ImageOutputDTO {

    private Long id;


    private String name;


    private byte[] image;

    private Long imageCollectionId;
}
