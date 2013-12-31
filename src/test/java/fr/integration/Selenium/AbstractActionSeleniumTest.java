package fr.integration.Selenium;

import java.io.File;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/applicationContext-test.xml" })
@WebAppConfiguration
public abstract class AbstractActionSeleniumTest {

	@Value("${base.url.http}")
	public String BASE_URL;

	public String baseUrl;

	@Value("${driver}")
	private String DRIVER;

	public WebDriver driver;
	public final static String DRIVER_FIREFOX = "FIREFOX";
	public final static String DRIVER_PHANTOMJS = "PHANTOMJS";
	public final static String PHANTOM_DIR_WINDOWS = "G:\\phantomjs.exe";

	@Before
	public void setUp() throws Exception {
		// Choix du driver : Firefox ou PhantomJS
		if (DRIVER.equals(DRIVER_FIREFOX)) {
			driver = new FirefoxDriver();
		}
		
		if (DRIVER.equals(DRIVER_PHANTOMJS)) {
			Capabilities capa = new DesiredCapabilities();
			PhantomJSDriverService.Builder builder = new PhantomJSDriverService.Builder();
			if (isWindows()) {
				builder.usingPhantomJSExecutable(new File(PHANTOM_DIR_WINDOWS));
			}
			PhantomJSDriverService service = builder.build();
			driver = new PhantomJSDriver(service, capa);
		}
	}

	private boolean isWindows() {
		String os = System.getProperty("os.name");
		if (os.toUpperCase().indexOf("WINDOWS") > -1) {
			return true;
		}
		return false;
	}
}
