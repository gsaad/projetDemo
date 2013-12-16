package fr.service;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.amazonaws.regions.Region;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;

import fr.service.impl.AmazonS3BucketImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/applicationContext-test.xml" })
public class AmazonS3BucketImplTest {
	private AmazonS3Bucket amazonS3Bucket;

	@Autowired
	 private AmazonS3Client amazonS3Client;
	 
	@Before
	public void setUp() throws Exception {
		amazonS3Bucket = new AmazonS3BucketImpl();
		amazonS3Client = EasyMock.createMock(AmazonS3Client.class);
		ReflectionTestUtils.setField(amazonS3Bucket, "amazonS3Client",
				amazonS3Client);
		reset(amazonS3Client);
	}
	
	@Test 
	public void testLoadFileInbucket() throws IOException{
		
		PutObjectResult putResult = new PutObjectResult();
		putResult.setVersionId("1");
		S3Object s3Object = new S3Object();
		s3Object.setKey("fichier1");
		expect(amazonS3Client.getObject(anyObject(GetObjectRequest.class))).andReturn(s3Object);
		
		replay(amazonS3Client);
		S3Object s3ObjectResponse = amazonS3Bucket.loadFileInbucket("fichier1.txt");
		assertEquals(s3Object.getKey(), s3ObjectResponse.getKey());
		verify(amazonS3Client);
	}
	
	@Test 
	public void testSaveFileInbucket() throws IOException{
		MockMultipartFile mockFile = new MockMultipartFile("test.txt",
				"Hello World".getBytes());
		PutObjectResult putResult = new PutObjectResult();
		putResult.setVersionId("1");
		expect(amazonS3Client.putObject(anyObject(PutObjectRequest.class))).andReturn(putResult);

		amazonS3Client.setRegion((Region)anyObject());
		EasyMock.expectLastCall().once();
		
		replay(amazonS3Client);
		
		amazonS3Bucket.saveFileInbucket(mockFile, "test_fichier");
		verify(amazonS3Client);
	}
}