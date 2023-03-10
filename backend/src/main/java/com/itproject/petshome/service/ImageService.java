package com.itproject.petshome.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.itproject.petshome.config.AWSProperties;
import com.itproject.petshome.exception.PetCreationFailure;
import com.itproject.petshome.model.Image;
import com.itproject.petshome.repository.ImageRepository;
import com.sun.xml.bind.v2.TODO;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.util.*;
import static org.apache.http.entity.ContentType.*;
@Data
@AllArgsConstructor
@Service
public class ImageService {
    private AmazonS3 s3Client;
    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);
    ImageRepository imageRepository;

    private void isImage(MultipartFile file) {
        if (!Arrays.asList(
                IMAGE_JPEG.getMimeType(),
                IMAGE_PNG.getMimeType(),
                IMAGE_GIF.getMimeType()).contains(file.getContentType())) {
            throw new IllegalStateException("File must be an image [" + file.getContentType() + "]");
        }

    }
    private ObjectMetadata extractMetadata(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());

        return objectMetadata;
    }
    private void isFileEmpty(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file [ " + file.getSize() + "]");
        }
    }

    void uploadPetImage(MultipartFile file, String path) throws PetCreationFailure {
        isFileEmpty(file);
        isImage(file);
        ObjectMetadata objectMetadata = extractMetadata(file);
        String filename = String.format("%s-%s", UUID.randomUUID(), file.getOriginalFilename());
        try {
            s3Client
                    .putObject(path, filename,
                            new BufferedInputStream(file.getInputStream()), objectMetadata);
            Image imageToSave = new Image();
            imageToSave.setUrl(path+filename);
            imageRepository.save(imageToSave);

        } catch (Exception e){
            logger.info(e.toString());
            //TODO: exception handling
            //throw new PetCreationFailure();
        }

    }
}
