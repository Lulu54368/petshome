package com.itproject.petshome.service;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.itproject.petshome.config.AWSProperties;
import com.itproject.petshome.dto.ImageCollectionDTO;
import com.itproject.petshome.dto.PetDTO;
import com.itproject.petshome.dto.input.ImageInput;
import com.itproject.petshome.dto.input.PetInput;
import com.itproject.petshome.dto.output.PetOutput;
import com.itproject.petshome.exception.DataNotValidException;
import com.itproject.petshome.exception.PetCreationFailure;
import com.itproject.petshome.exception.PetNotFound;
import com.itproject.petshome.mapper.ImageCollectionMapper;
import com.itproject.petshome.mapper.ImageMappper;
import com.itproject.petshome.mapper.PetMapper;
import com.itproject.petshome.model.ImageCollection;
import com.itproject.petshome.model.Pet;
import com.itproject.petshome.model.enums.*;
import com.itproject.petshome.repository.ImageCollectionRepository;
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
import java.io.ByteArrayInputStream;
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
    private ImageMappper imageMappper;
    private AmazonS3 s3Client;
    private AWSProperties awsProperties;
    private static final Logger logger=LoggerFactory.getLogger(PetService.class);
    @Transactional
    public PetDTO addPet(PetInput input) throws DataNotValidException, PetCreationFailure {
        Pet pet = petMapper.toEntity(input);
        pet = petRepository.save(pet);
        pet.getImageCollection().setPet(pet);
        ImageCollection imageCollection = pet.getImageCollection();
        List<ImageInput> images = input.getImages();
        if(images.size()==0) throw new DataNotValidException();
        for (int i = 0; i < images.size(); i++) {
            ImageCollection imageCollectionToSave = imageCollection.addImage(images.get(i), imageCollectionRepository);
            imageCollectionRepository.save(imageCollectionToSave);
        }
        try {
            //TODO: exception thrown when saving pet
            Pet petSaved = petRepository.save(pet);
            String folder_name = "pet_image_id_" + petSaved.getId() + "/";
            String path = awsProperties.getBucketName()+"/petshome";
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(0);
            s3Client
                    .putObject(path, folder_name,
                            new ByteArrayInputStream(new byte[0]), metadata);
        }
        catch (Exception e){
            petRepository.delete(pet);
            logger.info(String.valueOf(e));
            throw new PetCreationFailure();
        }

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
        for (int i = 0; i < petOutputs.size(); i++) {
            ImageCollectionDTO imageCollectionDTO = petOutputs.get(i).getImageCollectionDTO();
            if (imageCollectionDTO != null && !imageCollectionDTO.getImageList().isEmpty()) {
                petOutputs.get(i).setCover(imageCollectionDTO.getImageList().get(0));

            }
        }
        return  petOutputs;

    }

    public PetOutput viewPet(Long petId) throws PetNotFound {
        Pet pet = petRepository
                .findById(petId)
                .orElseThrow(PetNotFound::new);
        PetOutput res =  petMapper.toOutput(pet);
        return res;
    }

    public ImageCollectionDTO getImageCollectionByPet(Long petId) throws PetNotFound {
        return imageCollectionMapper.toDto(
                petRepository
                        .findById(petId)
                        .orElseThrow(PetNotFound::new)
                        .getImageCollection()
        );
    }



    public ImageCollectionDTO postImage(Long petId, ImageInput imageInput) throws PetNotFound {
        ImageCollection imageCollection= petRepository
                            .findById(petId)
                            .orElseThrow(PetNotFound::new)
                            .getImageCollection();
        ImageCollection imageCollectionToSave = imageCollection.addImage(imageInput, imageCollectionRepository);
        imageCollectionRepository.save(imageCollectionToSave);
        return imageCollectionMapper.toDto(imageCollection);
    }
}
