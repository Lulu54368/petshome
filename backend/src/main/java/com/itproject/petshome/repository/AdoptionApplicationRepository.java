package com.itproject.petshome.repository;

import com.itproject.petshome.dto.AdoptionApplicationDTO;
import com.itproject.petshome.model.AdoptionApplication;
import com.itproject.petshome.model.Pet;
import com.itproject.petshome.model.User;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdoptionApplicationRepository extends JpaRepository<AdoptionApplication, Long> {


    Optional<AdoptionApplication> findByUserAndPet(User user, Pet pet);

    boolean existsByUserAndPet(@NonNull User user, @NonNull Pet pet);

    List<AdoptionApplication> findByUser(User user);





}
