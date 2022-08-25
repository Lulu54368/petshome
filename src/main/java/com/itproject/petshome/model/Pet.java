package com.itproject.petshome.model;

import com.itproject.petshome.model.enums.Adopted;
import com.itproject.petshome.model.enums.Category;
import lombok.Data;

import javax.persistence.*;
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
    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<AdoptionApplication> adoptionApplications;
    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicants")
    private Set<User> users;*/



}
