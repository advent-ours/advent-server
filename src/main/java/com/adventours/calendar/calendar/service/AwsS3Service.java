package com.adventours.calendar.calendar.service;

import com.adventours.calendar.calendar.presentation.StorageBucketClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AwsS3Service {

    private final StorageBucketClient storageBucketClient;

    @Value("${cloud.aws.s3.dir}")
    private String bucketDir;

    public UploadKeyResponse getPresignedUrl(String fileExtension) {
        String uploadKey = generateObjectUploadKey();
        String objectName = bucketDir + "/" + uploadKey + "." + fileExtension.toLowerCase();
        return new UploadKeyResponse(
                uploadKey,
                storageBucketClient.generatePresignedUrl(objectName).toString());
    }

    private String generateObjectUploadKey() {
        return UUID.randomUUID().toString();
    }
}
