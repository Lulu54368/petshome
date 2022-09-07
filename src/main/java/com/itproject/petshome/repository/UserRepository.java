package com.itproject.petshome.repository;

import com.itproject.petshome.model.User;
import com.itproject.petshome.model.UserAdoptPet;
import org.mapstruct.Named;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);





}
