package com.adventours.calendar.calendar.service;

import com.adventours.calendar.calendar.presentation.StorageBucketClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AwsS3Service {

    private final StorageBucketClient storageBucketClient;

    public UploadKeyResponse getPresignedUrl(String fileExtension) {
        String uploadKey = generateObjectUploadKey();
        String objectName = uploadKey + "." + fileExtension.toLowerCase();
        return new UploadKeyResponse(
                uploadKey,
                storageBucketClient.generatePresignedUrl(objectName).toString());
    }

    private String generateObjectUploadKey() {
        return UUID.randomUUID().toString();
    }
}
