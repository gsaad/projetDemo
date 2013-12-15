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
import org.springframework.web.servlet.ModelAndView;

import fr.persistence.domain.Document;
import fr.persistence.domain.User;
import fr.service.DocumentService;
import fr.service.UserService;

@ContextConfiguration(locations = { "classpath:/META-INF/applicationContext-test.xml" })
@WebAppConfiguration
public class ListeDocsControllerTest extends AbstractJUnit4SpringContextTests {

	ListeDocsController listeDocsController;

	@Autowired
	private DocumentService documentService;

	@Autowired
	private UserService userService;

	@Before
	public void setup() {
		documentService = EasyMock.createMock(DocumentService.class);
		userService = EasyMock.createMock(UserService.class);
		listeDocsController = new ListeDocsController();
		ReflectionTestUtils.setField(listeDocsController, "documentService",
				documentService);
		ReflectionTestUtils.setField(listeDocsController, "userService",
				userService);
		reset(userService);
		reset(documentService);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDisplayListeDoc_userConnected() throws Exception {
		User user = new User();
		user.setPassword("pwd");
		user.setLogin("login1");
		expect(userService.getCurrentUser()).andReturn(user);

		Document doc = new Document();
		doc.setPk(1);
		doc.setIntitule("passeport");
		List<Document> listDocument = new ArrayList<Document>();
		listDocument.add(doc);
		expect(documentService.findListDocumentByLogin("login1")).andReturn(
				listDocument);
		replay(userService);
		replay(documentService);

		// when
		ModelAndView mav = listeDocsController.display();
		assertEquals("welcomeListeDocs", mav.getViewName());
		assertEquals(1,
				((List<Document>) mav.getModelMap().get("listDocs")).size());
		verify(userService);
		verify(documentService);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDisplayListeDoc_user_ko() throws Exception {
		expect(userService.getCurrentUser()).andReturn(new User());
		Document doc = new Document();
		doc.setPk(1);
		doc.setIntitule("passeport");
		List<Document> listDocument = new ArrayList<Document>();
		listDocument.add(doc);
		expect(documentService.findListDocumentByLogin(anyObject(String.class)))
				.andReturn(null);
		replay(userService);
		replay(documentService);

		// when
		ModelAndView mav = listeDocsController.display();
		assertEquals("welcomeListeDocs", mav.getViewName());
		assertEquals(0,
				((List<Document>) mav.getModelMap().get("listDocs")).size());
		verify(userService);
		verify(documentService);
	}
}
