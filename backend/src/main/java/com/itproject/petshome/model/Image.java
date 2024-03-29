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

    @Column(name = "filepath", nullable = false, length = 100000)
    private String filePath;
    @Column(name = "filename", unique = true,nullable = false )
    private String filename;
    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name="pet")
    private Pet pet;



}