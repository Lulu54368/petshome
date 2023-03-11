package com.itproject.petshome.model;


import com.itproject.petshome.model.enums.*;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "pet")
@Getter
@Setter
public class Pet {
    private  static Logger logger = LoggerFactory.getLogger(Pet.class);
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "adopted", nullable = false)
    private Adopted adopted = Adopted.NO;
    @Column(name = "category", nullable = false)
    private Category category;
    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL)
    private List<Image> imageList = new LinkedList<>();
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "detail")
    private String detail;
    @Column(name = "color")
    private Color color;
    @Column(name = "sex")
    private Sex sex ;
    @Column(name = "age", nullable = false)
    private int age;
    @Column(name = "personality")
    private String character;
    @Column(name = "immunization")
    private Immunization immunization ;
    @Column(name="city")
    private City city;
    @Column(name = "country")
    private Country country;
    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    Set<AdoptionApplication> adoptionApplications = new HashSet<>();

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    Set<UserAdoptPet> userAdoptPets = new HashSet<>();

    public synchronized Pet addImage(String path, String filename){
        Image image = new Image();
        image.setFilename(filename);
        image.setFilePath(path);
        image.setPet(this);
        this.imageList.add(image);
        return this;

    }




}
