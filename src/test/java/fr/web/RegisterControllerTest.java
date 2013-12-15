package fr.web;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.junit.Assert.assertEquals;
import static org.easymock.EasyMock.verify;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import fr.persistence.domain.User;
import fr.service.UserService;

@ContextConfiguration(locations = { "classpath:/META-INF/applicationContext-test.xml" })
@WebAppConfiguration
public class RegisterControllerTest extends AbstractJUnit4SpringContextTests {

	RegisterController registerController;
	@Autowired
	private UserService userService;

	@Before
	public void setup() {
		userService = EasyMock.createMock(UserService.class);
		registerController = new RegisterController();
		ReflectionTestUtils.setField(registerController, "userService",
				userService);
		reset(userService);
	}

	@Test
	public void testDisplay() {
		ModelAndView mav = registerController.display();
		assertEquals("accueilRegister", mav.getViewName());
		assertEquals(1, mav.getModel().size());
		assertEquals(new fr.persistence.domain.User(),
				(fr.persistence.domain.User) mav.getModel().get("user"));
	}

	@Test
	public void testRegister_form_ok() {
		User user = new User();
		user.setPassword("test1");
		user.setVerifyPassword("test1");
		user.setLogin("login1");
		user.setEmail("test@test.fr");
		user.setFirstName("first");
		user.setLastName("lastname");

		userService.createUser(user);
		EasyMock.expectLastCall();

		expect(userService.findUser("login1")).andReturn(null);
		replay(userService);

		ModelMap model = new ModelMap();
		ModelAndView mav = registerController.register(user, model);
		verify(userService);
		assertEquals("register_ok", mav.getViewName());
	}

	@Test
	public void testRegister_donnees_ko() {
		User user = new User();
		user.setPassword("test1");
		user.setVerifyPassword("test1");
		user.setLogin("login1");
		user.setFirstName("first");
		user.setLastName("lastname");
		ModelMap model = new ModelMap();
		ModelAndView mav = registerController.register(user, model);
		assertEquals("user.info.champs.obligatoire",
				mav.getModel().get("messageErreur"));
		assertEquals("accueilRegister", mav.getViewName());
	}

	@Test
	public void testRegister_password_ko() {
		User user = new User();
		user.setPassword("test1");
		user.setVerifyPassword("test2");
		user.setLogin("login1");
		user.setEmail("test@test.fr");
		user.setFirstName("first");
		user.setLastName("lastname");

		expect(userService.findUser("login1")).andReturn(null);
		replay(userService);

		ModelMap model = new ModelMap();
		ModelAndView mav = registerController.register(user, model);
		verify(userService);
		assertEquals("user.info.password.not.matching",
				mav.getModel().get("messageErreur"));
		assertEquals("accueilRegister", mav.getViewName());
	}

	@Test
	public void testRegister_password_login_existe() {
		User user = new User();
		user.setPassword("test1");
		user.setVerifyPassword("test2");
		user.setLogin("login1");
		user.setEmail("test@test.fr");
		user.setFirstName("first");
		user.setLastName("lastname");

		expect(userService.findUser("login1")).andReturn(user);
		replay(userService);

		ModelMap model = new ModelMap();
		ModelAndView mav = registerController.register(user, model);
		verify(userService);
		assertEquals("user.info.utilisateur.attribue",
				mav.getModel().get("messageErreur"));
		assertEquals("accueilRegister", mav.getViewName());
	}

	@Test
	public void testCancel() {
		assertEquals("redirect:/login", registerController.cancel());
	}
}
