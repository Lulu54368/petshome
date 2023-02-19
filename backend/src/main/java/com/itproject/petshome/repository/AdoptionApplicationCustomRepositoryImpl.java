package com.itproject.petshome.repository;

import com.itproject.petshome.model.AdoptionApplication;
import com.itproject.petshome.model.Pet;
import com.itproject.petshome.model.enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Repository
@Data
@AllArgsConstructor
public class AdoptionApplicationCustomRepositoryImpl implements AdoptionApplicationCustomRepository {
    final EntityManager em;

    @Override
    public List<AdoptionApplication> findAll(Optional<ApplicationStatus> status, Optional<Pet> pet) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<AdoptionApplication> query = cb.createQuery(AdoptionApplication.class);

        final Root<AdoptionApplication> adoptionApplicationRoot = query.from(AdoptionApplication.class);
        final ArrayList<Predicate> predicates = new ArrayList<>();

        status.ifPresent(c -> predicates.add(cb.equal(adoptionApplicationRoot.get("applicationStatus"), c)));
        pet.ifPresent(c -> predicates.add(cb.equal(adoptionApplicationRoot.get("pet"), c)));
        Predicate[] predicatesArray = new Predicate[predicates.size()];
        query.where(predicates.toArray(predicatesArray));
        List<AdoptionApplication> result = em.createQuery(query).getResultList();
        return result;

    }
}
