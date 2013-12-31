package fr.integration.Selenium;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class AccueilIT extends AbstractDBUnitSeleniumTest {

	@Before
	public void setUp() throws Exception {
		super.setUp();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testUrl() throws Exception {
		driver.get(BASE_URL);
		capturerScreenShot("accueil");
	}


	private void capturerScreenShot(String page) throws IOException {
		OutputType<byte[]> out = OutputType.BYTES;
		byte[] screenShot = ((TakesScreenshot) driver).getScreenshotAs(out);
		FileOutputStream fos = new FileOutputStream("target/screenshot_projetDemo_"
				+ page + ".png");
		fos.write(screenShot);
		fos.close();
	}
	
	@After
	public void tearDown() throws Exception {
		driver.close();
		driver.quit();
		driver = null;
	}
}
