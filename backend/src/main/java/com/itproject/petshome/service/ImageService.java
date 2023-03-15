package com.itproject.petshome.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
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
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.CompletedMultipartUpload;
import software.amazon.awssdk.services.s3.model.CompletedPart;
import software.amazon.awssdk.services.s3.model.UploadPartResponse;

import javax.imageio.stream.ImageInputStream;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static org.apache.http.entity.ContentType.*;
@Data
@AllArgsConstructor
@Service
public class ImageService {
    private AmazonS3 s3Client;
    private AWSProperties awsProperties;
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

    String uploadPetImage(MultipartFile file, String path, String bucket) throws PetCreationFailure {
        isFileEmpty(file);
        isImage(file);
        ObjectMetadata objectMetadata = extractMetadata(file);
        String filename = String.format( path+"%s-%s", UUID.randomUUID(), file.getOriginalFilename());
        try {
            partUpload(file, filename, bucket);

        } catch (Exception e) {
            logger.info(e.toString());
            //TODO: exception handling
            throw new PetCreationFailure();
        }
        return filename;

    }

    private void partUpload(MultipartFile largeFile, String key, String bucket) {
        final int PART_SIZE = 5*1024*1024;
        // Split the large file into parts
        List<ByteBuffer> partDataList = new ArrayList<>();
        long fileSize = largeFile.getSize();
        long remainingSize = fileSize;
        int partNumber = 1;
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(largeFile.getBytes())) {
            while (remainingSize > 0) {
                int partSize = (int) Math.min(PART_SIZE, remainingSize);
                byte[] partData = new byte[partSize];
                inputStream.read(partData);
                partDataList.add(ByteBuffer.wrap(partData));
                remainingSize -= partSize;
                partNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Initialize a multipart upload request
        //TODO: name of file
        InitiateMultipartUploadRequest initiateRequest =
                new InitiateMultipartUploadRequest(bucket, key);
        InitiateMultipartUploadResult createResponse = s3Client.initiateMultipartUpload(initiateRequest);
        String uploadId = createResponse.getUploadId();

        // Set up a thread pool to execute the upload tasks
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        // Submit upload tasks to the executor service and collect CompletableFutures
        List<CompletableFuture<CompletedPart>> completableFutures = new ArrayList<>();
        List<UploadPartResult> uploadPartResults = new ArrayList<>();
        for (int i = 0; i < partDataList.size(); i++) {
            int partNumberFinal = i + 1;
            ByteBuffer partData = partDataList.get(i);
            CompletableFuture<CompletedPart> completableFuture = CompletableFuture.supplyAsync(() -> {
                UploadPartRequest uploadRequest = new UploadPartRequest()
                        .withBucketName(bucket)
                        .withKey(key)
                        .withUploadId(uploadId)
                        .withPartNumber(partNumberFinal)
                        .withPartSize(partData.limit())
                        .withInputStream(RequestBody.fromByteBuffer(partData).contentStreamProvider().newStream());
                UploadPartResult uploadResponse = s3Client.uploadPart(uploadRequest);
                uploadPartResults.add(uploadResponse);
                logger.info(uploadResponse.toString());
                return CompletedPart.builder()
                        .partNumber(partNumberFinal)
                        .build();
            }, executorService);
            completableFutures.add(completableFuture);
        }

        // Wait for all CompletableFutures to complete and collect the results
        List<CompletedPart> completedParts = completableFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
        CompletedMultipartUpload completedMultipartUpload = CompletedMultipartUpload.builder()
                .parts(completedParts)
                .build();

        CompleteMultipartUploadRequest completeMultipartUploadRequest =
                new CompleteMultipartUploadRequest()
                        .withBucketName(bucket)
                        .withKey(key)
                        .withUploadId(uploadId)
                        .withPartETags(uploadPartResults);

        s3Client.completeMultipartUpload(completeMultipartUploadRequest);


    }

    public byte[] download(String filePath, String key) throws IOException {
        logger.info("file path "+ filePath+ "key "+ key);
        return IOUtils.toByteArray(s3Client
                .getObject(filePath, key)
                .getObjectContent());
    }
}
