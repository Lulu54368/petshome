package com.itproject.petshome.repository;

import com.itproject.petshome.model.UserAdoptPet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAdoptPetRepository extends JpaRepository<UserAdoptPet, Long> {
}
