package com.itproject.petshome.service;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.itproject.petshome.config.AWSProperties;
import com.itproject.petshome.dto.PetDTO;
import com.itproject.petshome.dto.input.PetInput;
import com.itproject.petshome.dto.output.PetOutput;
import com.itproject.petshome.exception.DataNotValidException;
import com.itproject.petshome.exception.PetCreationFailure;
import com.itproject.petshome.exception.PetNotFound;
import com.itproject.petshome.mapper.ImageMappper;
import com.itproject.petshome.mapper.PetMapper;
import com.itproject.petshome.model.Image;
import com.itproject.petshome.model.Pet;
import com.itproject.petshome.model.enums.*;
import com.itproject.petshome.repository.PetRepository;
import com.itproject.petshome.repository.PetRepositoryCustom;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;


@AllArgsConstructor
@Data
@Service
public class PetService {
    private PetRepository petRepository;
    private PetRepositoryCustom petRepositoryCustom;
    private PetMapper petMapper;
    private ImageMappper imageMappper;
    private AmazonS3 s3Client;
    private AWSProperties awsProperties;
    private static final Logger logger=LoggerFactory.getLogger(PetService.class);
    private ImageService imageService;
    @Transactional
    public PetDTO addPet(PetInput input) throws DataNotValidException, PetCreationFailure {
        Pet pet = petMapper.toEntity(input);
        List<MultipartFile> images = input.getImages();
        if(images.size()==0) throw new DataNotValidException();
        String folder_name = "pet_image_" + pet.getNickname()+"_"+ UUID.randomUUID() + "/";
        String path = awsProperties.getBucketName()+"/petshome";
        ThreadPoolExecutor executor =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(images.size());
        images
                .parallelStream()
                .map(image->{
                    return new Runnable() {
                        @Override
                        public void run() {
                            String filePath = path+folder_name;
                            logger.info(filePath);
                            try {
                                imageService.uploadPetImage(image, filePath);
                            } catch (PetCreationFailure e) {
                                logger.info(e.toString());
                                throw new RuntimeException(e);
                            }
                        }
                    };
                })
                .map(task-> CompletableFuture.runAsync(() -> {
                    try {
                        task.run();
                    } catch (Throwable e) {
                        logger.info(e.toString());
                    }
                }, executor))
                .collect(Collectors.toList());
        logger.info(pet.toString());
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

    public List<PetOutput> viewLostPet(Optional<Category> category,
                                            Optional<Adopted> adopted, Optional<Color> color,
                                            Optional<Sex> sex,
                                            Optional<Character> character,
                                            Optional<Integer> age, Optional<Immunization> immunization, Integer pageNo) {
        Pageable page = PageRequest.of(pageNo, 9);
        List<PetOutput> petOutputs =
                petRepositoryCustom
                .findByParameters(category, adopted, color, sex, character, age, immunization, page)
                .stream()
                .map(pet -> petMapper.toOutput(pet))
                .collect(Collectors.toList());
        // Set cover for PetOutput object

        return  petOutputs;

    }

    public PetOutput viewPet(Long petId) throws PetNotFound {
        Pet pet = petRepository
                .findById(petId)
                .orElseThrow(PetNotFound::new);
        PetOutput res =  petMapper.toOutput(pet);
        return res;
    }





}
