package com.itproject.petshome.repository;

import com.itproject.petshome.model.AdoptionApplication;
import com.itproject.petshome.model.Pet;
import com.itproject.petshome.model.enums.ApplicationStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface AdoptionApplicationCustomRepository {
    public List<AdoptionApplication>findAll(Optional<ApplicationStatus> status,
                                            Optional<Pet> pet);
}
