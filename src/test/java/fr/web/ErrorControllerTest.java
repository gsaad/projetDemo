package fr.web;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;

@ContextConfiguration(locations = { "classpath:/META-INF/applicationContext-test.xml" })
@WebAppConfiguration
public class ErrorControllerTest extends AbstractJUnit4SpringContextTests {

	ErrorController errorController = new ErrorController();

	@Test
	public void testPageNotFound() {
		assertEquals("404", errorController.pageNotFound());
	}

	@Test
	public void testInternatServerError() {
		assertEquals("500", errorController.internatServerError());
	}

}
