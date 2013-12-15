package fr.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;

import fr.service.AmazonS3Bucket;
@Transactional
@Service
public class AmazonS3BucketImpl implements AmazonS3Bucket{
	
	@Value("${amazon.bucket}")
	private String BUCKET_NAME;
	
	@Override
	public void saveFileInbucket(MultipartFile multipartFile,
			String nomFichier) throws IOException {
		AmazonS3 s3 = new AmazonS3Client(
				new ClasspathPropertiesFileCredentialsProvider());
		Region usWest2 = Region.getRegion(Regions.US_WEST_2);
		s3.setRegion(usWest2);
		byte[] contentBytes = null;
		InputStream is = null;
		try {
			is = multipartFile.getInputStream();
			contentBytes = IOUtils.toByteArray(is);
		} catch (IOException e) {
			System.err.printf("Failed while reading bytes from %s",
					e.getMessage());
		}

		try {
			// s3.createBucket(bucketName);
			PutObjectRequest request = new PutObjectRequest(BUCKET_NAME,
					nomFichier, createSampleFile(nomFichier, contentBytes));
			s3.putObject(request);
		} catch (AmazonServiceException ase) {
			System.out
					.println("Caught an AmazonServiceException, which means your request made it "
							+ "to Amazon S3, but was rejected with an error response for some reason.");
			System.out.println("Error Message:    " + ase.getMessage());
			System.out.println("HTTP Status Code: " + ase.getStatusCode());
			System.out.println("AWS Error Code:   " + ase.getErrorCode());
			System.out.println("Error Type:       " + ase.getErrorType());
			System.out.println("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			System.out
					.println("Caught an AmazonClientException, which means the client encountered "
							+ "a serious internal problem while trying to communicate with S3, "
							+ "such as not being able to access the network.");
			System.out.println("Error Message: " + ace.getMessage());
		}
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
