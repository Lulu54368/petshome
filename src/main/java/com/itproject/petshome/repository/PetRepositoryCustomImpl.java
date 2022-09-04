package com.itproject.petshome.repository;

import com.itproject.petshome.model.Pet;
import com.itproject.petshome.model.enums.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.MapUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Repository
@AllArgsConstructor
public class PetRepositoryCustomImpl implements PetRepositoryCustom{
    final EntityManager em;




    @Override
    public List<Pet> findByParameters(Optional<Category> category,
                                      Optional<Adopted> adopted,
                                      Optional<Color> color, Optional<Sex> sex, Optional<Character> character,
                                      Optional<Integer> age, Optional<Immunization> immunization) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Pet> query = cb.createQuery(Pet.class);

        final Root<Pet> pet = query.from(Pet.class);
        final ArrayList<Predicate> predicates = new ArrayList<>();

        category.ifPresent(c -> predicates.add(cb.equal(pet.get("category"), c)));
        adopted.ifPresent(c -> predicates.add(cb.equal(pet.get("adopted"), c)));
        color.ifPresent(c->predicates.add(cb.equal(pet.get("color"), c)));
        sex.ifPresent(c-> predicates.add(cb.equal(pet.get("sex"), c)));
        character.ifPresent(c->predicates.add(cb.equal(pet.get("character"), c)));
        age.ifPresent(c->predicates.add(cb.equal(pet.get("age"), c)));
        immunization.ifPresent(c->predicates.add(cb.equal(pet.get("immunization"), c)));
        Predicate[] predicatesArray = new Predicate[predicates.size()];
        query.where(predicates.toArray(predicatesArray));
        List<Pet> result = em.createQuery(query).getResultList();

        return result;

    }
}
