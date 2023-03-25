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
import com.itproject.petshome.mapper.PetMapper;
import com.itproject.petshome.model.Image;
import com.itproject.petshome.model.Pet;
import com.itproject.petshome.model.enums.*;
import com.itproject.petshome.repository.ImageRepository;
import com.itproject.petshome.repository.PetRepository;
import com.itproject.petshome.repository.PetRepositoryCustom;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.joda.time.field.MillisDurationField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import javax.validation.Valid;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import static reactor.core.publisher.Mono.delay;


@AllArgsConstructor
@Data
@Service
public class PetService {
    private PetRepository petRepository;
    private PetRepositoryCustom petRepositoryCustom;
    private PetMapper petMapper;
    private AmazonS3 s3Client;
    private AWSProperties awsProperties;
    private static final Logger logger=LoggerFactory.getLogger(PetService.class);
    private ImageService imageService;
    private ImageRepository imageRepository;
    @Transactional
    public PetDTO addPet(@Valid PetInput  input) throws DataNotValidException, PetCreationFailure {
        Pet pet = petMapper.toEntity(input);
        List<MultipartFile> images = input.getImages();
        if(images == null ||images.size()==0) throw new DataNotValidException();
        String folder_name = "pet_image_" + pet.getNickname()+"_"+ UUID.randomUUID() + "/";
        String path = "petshome/";
        ThreadPoolExecutor executor =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(Math.min(images.size(), 10));
        images
                .parallelStream()
                .map(image-> CompletableFuture.runAsync(() -> {
                    try {
                        String filename;
                        String filePath = path+folder_name;
                        try {

                            filename =
                                    imageService.uploadPetImage(image, filePath, awsProperties.getBucketName());


                        } catch (PetCreationFailure e) {
                            throw new RuntimeException(e);
                        }
                        if(filename != null)
                            pet.addImage(folder_name, filename);
                    } catch (Throwable e) {
                        logger.debug(e.toString());
                    }
                }, executor)
                        .join())
                .collect(Collectors.toList());
        List<String> imageList = getPetImageList(pet);
        petRepository.save(pet);
        PetDTO petDTO = petMapper.toDto(pet);
        petDTO.setImages(imageList);
        return petDTO;
    }
    private List<String> getPetImageList(Pet pet){
        List<String> result = pet.getImageList()
                .parallelStream()
                .map(image->image.getFilename())
                .collect(Collectors.toList());
        return result;
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
        List<Image> images = imageRepository.findByPet(pet);
        ThreadPoolExecutor executor =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(Math.min(images.size(), 10));
        //TODO: exception throw scenario
        if(images != null && images.size()!= 0){
            images
                    .parallelStream()
                    .map(image-> CompletableFuture.runAsync(() -> {
                                imageService.deleteImage(image.getFilePath(), awsProperties.getBucketName());
                            }, executor)
                            .join())
                    .collect(Collectors.toList());
        }
        petRepository.delete(pet);
        return petMapper.toDto(pet);
    }

    public Flux<PetOutput> viewLostPet(Optional<Category> category,
                                       Optional<Adopted> adopted, Optional<Color> color,
                                       Optional<Sex> sex,
                                       Optional<Character> character,
                                       Optional<Integer> age, Optional<Immunization> immunization, Integer pageNo) {
        Pageable page = PageRequest.of(pageNo, 9);
        Flux<PetOutput> petOutputs =
                Flux.fromStream(petRepositoryCustom
                        .findByParameters(category, adopted, color, sex, character, age, immunization, page)
                        .stream()
                        .map(pet -> {
                            PetOutput petOutput = petMapper.toOutput(pet);
                                    petOutput.setCover(pet.getImageList().size() > 0 ? pet.getImageList().get(0).getFilename()
                                            : null);
                                    return petOutput;
                                }
                        ));
        return  petOutputs;

    }


    public PetDTO viewPet(Long petId) throws PetNotFound {
        Pet pet = petRepository
                .findById(petId)
                .orElseThrow(PetNotFound::new);
        PetDTO res =  petMapper.toDto(pet);
        res.setImages(getPetImageList(pet));
        return res;
    }





}
