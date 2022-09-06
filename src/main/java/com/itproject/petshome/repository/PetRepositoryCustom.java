package com.itproject.petshome.repository;

import com.itproject.petshome.model.Pet;
import com.itproject.petshome.model.enums.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public interface PetRepositoryCustom {

    public List<Pet> findByParameters(Optional<Category> category,
                                      Optional<Adopted> adopted, Optional<Color> color,
                                      Optional<Sex> sex, Optional<Character> character,
                                      Optional<Integer> age,
                                      Optional<Immunization> immunization);

   public void delete(Pet pet);
}
