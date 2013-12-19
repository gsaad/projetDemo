package fr.web;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import fr.persistence.domain.Document;
import fr.persistence.domain.User;
import fr.service.DocumentService;
import fr.service.UserService;

@ContextConfiguration(locations = { "classpath:/META-INF/applicationContext-test.xml" })
@WebAppConfiguration
public class LoginControllerTest extends AbstractJUnit4SpringContextTests {

	LoginController loginController;

	@Autowired
	private UserService userService;

	@Autowired
	private DocumentService documentService;

	@Before
	public void setup() {
		userService = EasyMock.createMock(UserService.class);
		documentService = EasyMock.createMock(DocumentService.class);
		loginController = new LoginController();
		ReflectionTestUtils.setField(loginController, "userService",
				userService);
		ReflectionTestUtils.setField(loginController, "documentService",
				documentService);
		reset(userService);
		reset(documentService);
	}

	@Test
	public void testLogin_user_connecte() {
		User user = new User();
		user.setPassword("pwd");
		user.setLogin("login1");
		expect(userService.getCurrentUser()).andReturn(user);

		Document doc = new Document();
		doc.setPk(1);
		doc.setIntitule("passeport");
		List<Document> listDocument = new ArrayList<Document>();
		listDocument.add(doc);
		expect(documentService.findListDocumentByLogin(anyObject(String.class)))
				.andReturn(null);

		replay(userService);

		RedirectAttributes redirectAttr = new RedirectAttributesModelMap();
		ModelAndView mav = loginController.login(redirectAttr);
		verify(userService);
		assertEquals("redirect:/document/listeDocs", mav.getViewName());
	}

	@Test
	public void testLogin_user_non_connecte() {

		expect(userService.getCurrentUser()).andReturn(null);
		replay(userService);

		RedirectAttributes redirectAttr = new RedirectAttributesModelMap();
		ModelAndView mav = loginController.login(redirectAttr);
		verify(userService);

		assertEquals(new User(), mav.getModel().get("user"));
		assertEquals("accueilLogin", mav.getViewName());
	}

	@Test
	public void testLoginFailed() {
		ModelMap model = new ModelMap();
		ModelAndView mv = loginController.loginfailed(model);
		assertEquals("accueilLogin", mv.getViewName());
	}

	@Test
	public void testLogout() {
		ModelMap model = new ModelMap();
		String view = loginController.logout(model);
		assertEquals("accueilLogin", view);
	}

}
