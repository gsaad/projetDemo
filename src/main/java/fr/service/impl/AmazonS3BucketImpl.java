package fr.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import fr.service.AmazonS3Bucket;
import fr.service.BusinessServiceException;
@Transactional
@Service
public class AmazonS3BucketImpl implements AmazonS3Bucket{
	
	@Value("${amazon.bucket}")
	private String BUCKET_NAME;
	
	 @Autowired
	 private AmazonS3Client amazonS3Client;
	
	@Override
	public void saveFileInbucket(MultipartFile multipartFile,
			String nomFichier) throws BusinessServiceException {
		try{
			Region usWest2 = Region.getRegion(Regions.US_WEST_2);
			amazonS3Client.setRegion(usWest2);
			byte[] contentBytes = null;
			InputStream is = null;
			is = multipartFile.getInputStream();
			contentBytes = IOUtils.toByteArray(is);
			PutObjectRequest request = new PutObjectRequest(BUCKET_NAME,
					nomFichier, createSampleFile(nomFichier, contentBytes));
			amazonS3Client.putObject(request);
		}catch(IOException io){
			throw new BusinessServiceException("Impossible d'enregister le fichier", io.getCause());
		}
	}
	
	@Override
	public S3Object loadFileInbucket(String nomFichier) {
		S3Object s3object = amazonS3Client.getObject(new GetObjectRequest(
				BUCKET_NAME, nomFichier));
		return s3object;
	}
	
	protected File createSampleFile(String fileName, byte[] bytes)
			throws IOException {
			File file = File.createTempFile(fileName, "");
			file.deleteOnExit();
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(file));
			stream.write(bytes);
			stream.close();
			return file;
	}
}
