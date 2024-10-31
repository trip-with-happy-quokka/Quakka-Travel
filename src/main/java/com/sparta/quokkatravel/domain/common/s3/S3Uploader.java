package com.sparta.quokkatravel.domain.common.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
@Service
public class S3Uploader {

    private final AmazonS3 amazonS3;
    private final String bucket;

    public S3Uploader(AmazonS3 amazonS3, @Value("${cloud.aws.s3.bucket}") String bucket) {
        this.amazonS3 = amazonS3;
        this.bucket = bucket;
    }

    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        // 파일 이름 생성
        String originalFilename = multipartFile.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String uniqueFilename = uuid + "_" + (originalFilename != null ? originalFilename.replaceAll("[^a-zA-Z0-9.-]", "_") : "unknown");

        String filename = dirName + "/" + uniqueFilename;

        // S3에 업로드
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());  // ContentType 설정

        amazonS3.putObject(new PutObjectRequest(bucket, filename, multipartFile.getInputStream(), metadata));

        return amazonS3.getUrl(bucket, filename).toString();
    }

    // S3에서 파일 삭제
    public void delete(String fileName) {
        try {
            // URL 인코딩되어 있는 경우 디코딩
            String decodedFileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
            log.info("Deleting file from S3: " + decodedFileName);
            amazonS3.deleteObject(bucket, decodedFileName);
        } catch (Exception e) {
            log.error("Error while deleting the file: {}", e.getMessage());
        }
    }

    // 파일을 삭제하고 새 파일로 업데이트
    public String updateFile(MultipartFile newFile, String oldFileName, String dirName) throws IOException {
        // 기존 파일 삭제
        log.info("S3 oldFileName: " + oldFileName);
        delete(oldFileName);
        // 새 파일 업로드
        return upload(newFile, dirName);
    }
}