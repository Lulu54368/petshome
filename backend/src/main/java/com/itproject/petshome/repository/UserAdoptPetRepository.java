package com.itproject.petshome.repository;

import com.itproject.petshome.model.User;
import com.itproject.petshome.model.UserAdoptPet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAdoptPetRepository extends JpaRepository<UserAdoptPet, Long> {
    List<UserAdoptPet> findByUser(User user);

}
