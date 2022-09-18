package com.itproject.petshome.repository;

import com.itproject.petshome.model.AdoptionApplication;
import com.itproject.petshome.model.User;
import com.itproject.petshome.model.VolunteerApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Repository
public interface VolunteerApplicationRepository extends JpaRepository<VolunteerApplication, Long> {
    boolean existsByUser(@NotNull User user);

    Optional<VolunteerApplication> findByUser(User user);
}
