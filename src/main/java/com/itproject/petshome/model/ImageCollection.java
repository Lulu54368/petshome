package com.itproject.petshome.model;

import com.itproject.petshome.dto.input.ImageInput;
import com.itproject.petshome.repository.ImageCollectionRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "image_collection")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;


    @OneToMany(mappedBy = "imageCollection", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Image> imageList = new LinkedList<>();
    @OneToOne()
    @JoinColumn(name="pet",referencedColumnName = "id")
    private Pet pet;

    public ImageCollection addImage(ImageInput imageInput, ImageCollectionRepository imageCollectionRepository){
        Image image = new Image();
        image.setImage(imageInput.getImage());
        this.imageList.add(image);

        imageList.get(imageList.size()-1).setImageCollection(this);
        return this;

    }
}
