package fr.service;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.S3Object;

public interface AmazonS3Bucket {

	void saveFileInbucket(MultipartFile multipartFile, String nomFichier) throws BusinessServiceException;

	S3Object loadFileInbucket(String nomFichier);
}
