package fr.web;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.view.RedirectView;

import com.amazonaws.AmazonServiceException;

import fr.service.BusinessServiceException;
@ContextConfiguration(locations = { "classpath:/META-INF/applicationContext-test.xml" })
@WebAppConfiguration
public class ExceptionControllerTest extends AbstractJUnit4SpringContextTests {

	ExceptionController exceptionController;

	@Before
	public void setup() {
		exceptionController = new ExceptionController();
	}

	@Test
	public void testBusinessServiceException(){
		BusinessServiceException exception = new BusinessServiceException("erreur de données");
		MockHttpServletRequest request = new MockHttpServletRequest();
		RedirectView rv = exceptionController.handleBusinessServiceException(exception, request);
		assertEquals("listeDocs", rv.getUrl());
	}
	
	@Test
	public void testAmazonException(){
		AmazonServiceException exception = new AmazonServiceException("erreur de données");
		MockHttpServletRequest request = new MockHttpServletRequest();
		RedirectView rv = exceptionController.handleAmazonException(exception, request);
		assertEquals("listeDocs", rv.getUrl());
	}
	
	@Test
	public void tesMaxUploadSizeExceededException(){
		MaxUploadSizeExceededException exception = new MaxUploadSizeExceededException(100000);
		MockHttpServletRequest request = new MockHttpServletRequest();
		RedirectView rv = exceptionController.handleMaxUploadSizeExceededException(exception, request);
		assertEquals("addDocForm", rv.getUrl());
	}
}