package com.itproject.petshome.repository;

import com.itproject.petshome.model.Image;
import com.itproject.petshome.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByPet(Pet pet);

}
