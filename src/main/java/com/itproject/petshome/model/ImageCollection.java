package com.itproject.petshome.model;

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
    @Column(name="cover", nullable = false)
    private Long first = Long.valueOf(1);




}
