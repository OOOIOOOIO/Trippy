package com.sh.trippy.global.util.aws;

import com.sh.trippy.global.exception.CustomErrorCode;
import com.sh.trippy.global.exception.CustomException;
import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Operations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.exception.SdkClientException;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AmazonS3Service {

    @Value("${spring.cloud.aws.region.static}")
    private String region;
    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;
    private final S3Operations s3Operations;
    public String uploadFile(MultipartFile file){

        String fileUrl = "None";
        try {
            String fileName = "trippy/" + file.getOriginalFilename();
            fileUrl= "https://" + bucket + ".s3." + region + ".amazonaws.com/" + fileName;

            ObjectMetadata metadata = ObjectMetadata.builder().contentType(file.getContentType()).build();

            s3Operations.upload(bucket, fileName, file.getInputStream(), metadata);

            return fileUrl;

        } catch (IOException e) {

            log.info("[error] : " + e.getMessage());
            throw new CustomException(CustomErrorCode.FailToUploadFileS3Exception);
        }

    }

    public void delete(String fileUrl) {
        try {
            s3Operations.deleteObject(fileUrl);
        } catch (SdkClientException e) {
            throw new CustomException(CustomErrorCode.FailToDeleteFileS3Exception);
        }
    }
}
