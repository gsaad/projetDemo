package fr.web;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;

import fr.persistence.domain.User;
import fr.service.UserService;

@ContextConfiguration(locations = { "classpath:/META-INF/applicationContext-test.xml" })
@WebAppConfiguration

public class ModifierUserControllerTest  extends AbstractJUnit4SpringContextTests {

	ModifierUserController modifierUserController;
	
	@Autowired
	private UserService userService ; 
	
	@Before
	public void setup() {
		userService = EasyMock.createMock(UserService.class);
		modifierUserController = new ModifierUserController();
		ReflectionTestUtils.setField(modifierUserController, "userService", userService);
		reset(userService);
	}
	@Test    
	public void display() {
		User user = new User();
		user.setPassword("pwd");
		user.setLogin("login1");
		expect(userService.getCurrentUser()).andReturn(user);
		replay(userService);
		ModelAndView mav = modifierUserController.display();
		verify(userService);
		assertEquals(user, mav.getModel().get("user"));
		assertEquals("welcomeModifierUser", mav.getViewName());
	    }
	@Test
	public void testModifierUser_form_ok() {
		User user = new User();
		user.setLogin("login1");
		user.setPassword("test1");
		user.setVerifyPassword("test1");
		user.setEmail("test@test.fr");
		user.setFirstName("first");
		user.setLastName("lastname");
		expect(userService.getCurrentUser()).andReturn(user);
		
		userService.updateUser(user);
	    EasyMock.expectLastCall();

	    replay(userService);
//		BindingResult errors = new BindException(user, "user");
		ModelMap model = new ModelMap();
		ModelAndView mav = modifierUserController.modifierUser(user, model);
		
		verify(userService);
		assertEquals("modifierUser_ok", mav.getViewName());
	}
	
	@Test
	public void testModifierUser_password_ko() {
		User user = new User();
		user.setPassword("test1");
		user.setVerifyPassword("test2");
		
		user.setEmail("test@test.fr");
		user.setFirstName("first");
		user.setLastName("lastname");
		
		replay(userService);
		
		BindingResult errors = new BindException(user, "user");
		ModelMap model = new ModelMap();
		ModelAndView mav = modifierUserController.modifierUser( user, model);
		verify(userService);
		assertEquals(0, errors.getAllErrors().size());
		assertEquals("user.info.password.not.matching", mav.getModel().get("messageErreur"));
		assertEquals("welcomeModifierUser", mav.getViewName());
	}
	
	@Test
	public void testRegister_password_absent() {
		User user = new User();
		user.setLogin("login1");
		user.setEmail("test@test.fr");
		user.setFirstName("first");
		user.setLastName("lastname");
		
		replay(userService);
		
		ModelMap model = new ModelMap();
		ModelAndView mav = modifierUserController.modifierUser(user, model);
		verify(userService);
		assertEquals("user.info.champs.obligatoire", mav.getModel().get("messageErreur"));
		assertEquals("welcomeModifierUser", mav.getViewName());
	}

	@Test
	public void cancel() {
		String view = modifierUserController.cancel();
		assertEquals("redirect:/listeDocs", view);
	}
}
