package com.itproject.petshome.repository;

import com.itproject.petshome.model.Donation;
import com.itproject.petshome.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByUser(User user);
}
