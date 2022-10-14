package com.itproject.petshome.repository;

import com.itproject.petshome.dto.ImageCollectionDTO;
import com.itproject.petshome.model.ImageCollection;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ImageCollectionRepository extends JpaRepository<ImageCollection, Long> {
    Optional<ImageCollection> findById(Long id);
}
