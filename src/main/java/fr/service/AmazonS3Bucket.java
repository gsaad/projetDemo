package fr.service;

import java.io.IOException;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public interface AmazonS3Bucket {

	void saveFileInbucket(CommonsMultipartFile multipartFile, String nomFichier)
			throws IOException;

}
