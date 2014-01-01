package fr.integration.Selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class AccueilIT extends AbstractDBUnitSeleniumTest {

	private static int compteur = 0;
	@Before
	public void setUp() throws Exception {
		super.setUp();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testRegister() throws Exception {
		try {

			driver.get(BASE_URL);
			driver.findElement(By.linkText("Enregistrement")).click();
			driver.findElement(By.id("lastname")).clear();
			driver.findElement(By.id("lastname")).sendKeys("selenium");
			driver.findElement(By.id("lastName")).clear();
			driver.findElement(By.id("lastName")).sendKeys("selenium Prenom");
			driver.findElement(By.id("firstName")).clear();
			driver.findElement(By.id("firstName")).sendKeys("selenium Name");
			driver.findElement(By.id("email")).clear();
			driver.findElement(By.id("email")).sendKeys("selenium@gmail.com");
			driver.findElement(By.id("password")).clear();
			driver.findElement(By.id("password")).sendKeys("selenium");
			driver.findElement(By.id("verifyPassword")).clear();
			driver.findElement(By.id("verifyPassword")).sendKeys("selenium");
			capturerScreenShot("register");
			
			driver.findElement(By.name("submit")).click();
			driver.findElement(By.linkText("connecter")).click();
			assertEquals("Connectez-vous",
					driver.findElement(By.cssSelector("legend")).getText());
			driver.findElement(By.id("j_username")).clear();
			driver.findElement(By.id("j_username")).sendKeys("selenium");
			driver.findElement(By.id("j_password")).clear();
			driver.findElement(By.id("j_password")).sendKeys("selenium");
			driver.findElement(By.name("submit")).click();
			assertTrue(matchText("La liste est vide"));
			capturerScreenShot("liste_document_vide");
			driver.findElement(By.cssSelector("button.btn.btn-primary"))
					.click();
			assertEquals("Ajout d''un nouveau document",
					driver.findElement(By.cssSelector("legend")).getText());
			driver.findElement(By.cssSelector("a > button.btn.btn-primary"))
					.click();
			assertTrue(matchText("La liste est vide"));
			driver.findElement(By.linkText("Déconnexion")).click();
			assertTrue(matchText("Connectez-vous"));
			driver.findElement(By.id("j_username")).clear();
			driver.findElement(By.id("j_username")).sendKeys("test_login1");
			driver.findElement(By.id("j_password")).clear();
			driver.findElement(By.id("j_password")).sendKeys("test_login");
			driver.findElement(By.name("submit")).click();
			assertTrue(matchText("Le nom d'utilisateur ou le mot de passe saisi est incorrect"));
			driver.findElement(By.id("j_username")).clear();
			driver.findElement(By.id("j_username")).sendKeys("test_login1");
			driver.findElement(By.id("j_password")).clear();
			driver.findElement(By.id("j_password")).sendKeys("password");
			driver.findElement(By.name("submit")).click();
			assertTrue(matchText("passeport 1"));
			assertEquals("1 à 5 sur 5 documents",
					driver.findElement(By.id("example_info")).getText());
			driver.findElement(By.cssSelector("i.icon-trash")).click();
			driver.findElement(By.id("hrefbtn")).click();
			assertTrue(matchText("Confirmation de suppression du document : passeport 1."));
			assertEquals("1 à 4 sur 4 documents",
					driver.findElement(By.id("example_info")).getText());
			driver.findElement(By.linkText("Déconnexion")).click();
			assertEquals("Connectez-vous",
					driver.findElement(By.cssSelector("legend")).getText());
			driver.findElement(By.linkText("Enregistrement")).click();
			assertEquals("Informations personnelles",
					driver.findElement(By.cssSelector("legend")).getText());
			capturerScreenShot("modifier_user");
			driver.findElement(By.id("lastname")).clear();
			driver.findElement(By.id("lastname")).sendKeys("selenium");
			driver.findElement(By.id("lastName")).clear();
			driver.findElement(By.id("lastName")).sendKeys("selenium");
			driver.findElement(By.id("firstName")).clear();
			driver.findElement(By.id("firstName")).sendKeys("selenium Name");
			driver.findElement(By.id("email")).clear();
			driver.findElement(By.id("email")).sendKeys("selenium@gmail.com");
			driver.findElement(By.id("password")).clear();
			driver.findElement(By.id("password")).sendKeys("selenium");
			driver.findElement(By.id("verifyPassword")).clear();
			driver.findElement(By.id("verifyPassword")).sendKeys("selenium");
			driver.findElement(By.name("submit")).click();
			assertTrue(matchText("Le login est déjà attribué"));
			driver.findElement(By.cssSelector("a > button.btn.btn-primary"))
					.click();
			assertEquals("Connectez-vous",
					driver.findElement(By.cssSelector("legend")).getText());
		} finally {
			capturerScreenShot("accueil");
		}
	}

	private boolean matchText(String libelle) {
		return driver
				.findElement(By.cssSelector("BODY"))
				.getText()
				.matches(
						"^[\\s\\S]*" + libelle + "[\\s\\S]*$");
	}

	private void capturerScreenShot(String page) throws IOException {
		final String SEPARATEUR = "_";
		OutputType<byte[]> out = OutputType.BYTES;
		byte[] screenShot = ((TakesScreenshot) driver).getScreenshotAs(out);
		StringBuilder fileName = new StringBuilder("");
		fileName.append("target/screenshot")
				.append(SEPARATEUR)
				.append(Thread.currentThread().getStackTrace()[2]
						.getMethodName()).append(SEPARATEUR).append(compteur++)
				.append(SEPARATEUR).append(page).append(".png");
		FileOutputStream fos = new FileOutputStream(fileName.toString());
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
