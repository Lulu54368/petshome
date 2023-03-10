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

    @Column(name = "url", unique = true, nullable = true, length = 100000)
    private String url;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name="pet")
    private Pet pet;

}