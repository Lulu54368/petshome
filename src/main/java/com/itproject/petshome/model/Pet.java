package com.itproject.petshome.model;

import com.itproject.petshome.model.enums.Adopted;
import com.itproject.petshome.model.enums.Category;
import com.itproject.petshome.model.enums.Color;
import com.itproject.petshome.model.enums.Immunization;
import com.itproject.petshome.model.enums.Sex;
import lombok.Data;

import javax.persistence.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pet")
@Data
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "adopted", nullable = false)
    private Adopted adopted = Adopted.NO;
    @Column(name = "category", nullable = false)
    private Category category;
    @Column(name = "picture")
    private byte[] picture;
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
    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<AdoptionApplication> adoptionApplications = new HashSet<>();

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<UserAdoptPet> userAdoptPets = new HashSet<>();







}
