package fr.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.amazonaws.services.s3.model.S3Object;

public interface AmazonS3Bucket {

	void saveFileInbucket(MultipartFile multipartFile, String nomFichier)
			throws IOException;

	S3Object loadFileInbucket(String nomFichier) throws IOException;

}
