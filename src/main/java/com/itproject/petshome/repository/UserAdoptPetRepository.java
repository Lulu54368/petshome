package com.itproject.petshome.repository;

import com.itproject.petshome.model.UserAdoptPet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAdoptPetRepository extends JpaRepository<UserAdoptPet, Long> {
}
