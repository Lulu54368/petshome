package com.itproject.petshome.repository;

import com.itproject.petshome.model.AdoptionApplication;
import com.itproject.petshome.model.VolunteerApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolunteerApplicationRepository extends JpaRepository<VolunteerApplication, Long> {
}
