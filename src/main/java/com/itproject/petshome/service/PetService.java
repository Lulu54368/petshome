package com.itproject.petshome.service;

import com.itproject.petshome.dto.ImageCollectionDTO;
import com.itproject.petshome.dto.PetDTO;
import com.itproject.petshome.dto.input.ImageInput;
import com.itproject.petshome.dto.input.PetInput;
import com.itproject.petshome.exception.PetNotFound;
import com.itproject.petshome.mapper.ImageCollectionMapper;
import com.itproject.petshome.mapper.PetMapper;
import com.itproject.petshome.model.ImageCollection;
import com.itproject.petshome.model.Pet;
import com.itproject.petshome.model.enums.*;
import com.itproject.petshome.repository.ImageCollectionRepository;
import com.itproject.petshome.repository.PetRepository;
import com.itproject.petshome.repository.PetRepositoryCustom;
import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
@Service
public class PetService {
    private PetRepository petRepository;
    private PetRepositoryCustom petRepositoryCustom;
    private ImageCollectionRepository imageCollectionRepository;
    private PetMapper petMapper;
    private ImageCollectionMapper imageCollectionMapper;
    @Transactional
    public PetDTO addPet(PetInput input) {
        Pet pet = petMapper.toEntity(input);

        pet = petRepository.save(pet);
        pet.getImageCollection().setPet(pet);
        petRepository.save(pet);
        return petMapper.toDto(pet);

    }






    public PetDTO updatePet(Long id) throws PetNotFound {
        Pet pet = petRepository.findById(id)
                .orElseThrow(PetNotFound::new);
        pet.setAdopted(Adopted.YES);
        petRepository.save(pet);
        return petMapper.toDto(pet);

    }

    public PetDTO deletePet(Long petId) throws PetNotFound {
        Pet pet = petRepository
                .findById(petId)
                .orElseThrow(PetNotFound::new);

        petRepository.delete(pet);
        return petMapper.toDto(pet);
    }

    public List<PetDTO> viewLostPet(Optional<Category> category,
                                    Optional<Adopted> adopted, Optional<Color> color,
                                    Optional<Sex> sex,
                                    Optional<Character> character,
                                    Optional<Integer> age, Optional<Immunization> immunization, Integer pageNo) {
        Pageable page = PageRequest.of(pageNo, 10);
        return petRepositoryCustom
                .findByParameters(category, adopted, color, sex, character, age, immunization, page)
                .stream()
                .map(pet->petMapper.toDto(pet))
                .collect(Collectors.toList());

    }

    public PetDTO viewPet(Long petId) throws PetNotFound {
        return petMapper
                .toDto(petRepository
                        .findById(petId)
                        .orElseThrow(PetNotFound::new));
    }

    public ImageCollectionDTO getImageCollectionByPet(Long petId) throws PetNotFound {
        return imageCollectionMapper.toDto(
                petRepository
                        .findById(petId)
                        .orElseThrow(PetNotFound::new)
                        .getImageCollection()
        );
    }



    public ImageCollectionDTO postImage(Long petId, ImageInput imageInput, SetToCover setToCover) throws PetNotFound {
        ImageCollection imageCollection= petRepository
                            .findById(petId)
                            .orElseThrow(PetNotFound::new)
                            .getImageCollection();
        ImageCollection imageCollectionToSave = imageCollection.addImage(imageInput, imageCollectionRepository);
        imageCollectionRepository.save(imageCollectionToSave);
        return imageCollectionMapper.toDto(imageCollection);
    }
}
