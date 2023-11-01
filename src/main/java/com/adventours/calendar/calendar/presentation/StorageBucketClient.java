package com.adventours.calendar.calendar.presentation;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class StorageBucketClient {

    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public URL generatePresignedUrl(String objectName) {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 30; // 30분
        expiration.setTime(expTimeMillis);
        return s3Client.generatePresignedUrl(bucketName, objectName, expiration, HttpMethod.PUT);
    }
}
