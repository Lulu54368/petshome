package com.itproject.petshome.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "image")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "image", unique = false, nullable = false, length = 100000)
    private byte[] image;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name="image_collection")
    private ImageCollection imageCollection;

}