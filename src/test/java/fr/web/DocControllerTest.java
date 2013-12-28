package fr.web;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.easymock.EasyMock;
import org.easymock.IAnswer;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import fr.persistence.domain.Document;
import fr.persistence.domain.TypeDocument;
import fr.persistence.domain.User;
import fr.service.BusinessServiceException;
import fr.service.DocumentService;
import fr.service.UserService;
import fr.web.form.DocumentForm;
@ContextConfiguration(locations = { "classpath:/META-INF/applicationContext-test.xml" })
@WebAppConfiguration
public class DocControllerTest extends AbstractJUnit4SpringContextTests {

	DocController docController;

	@Autowired
	private DocumentService documentService;

	@Autowired
	private UserService userService;

	@Autowired
	private MessageSource messageSource;

	@Before
	public void setup() {
		documentService = EasyMock.createMock(DocumentService.class);
		userService = EasyMock.createMock(UserService.class);
		messageSource = EasyMock.createMock(MessageSource.class);
		docController = new DocController();
		ReflectionTestUtils.setField(docController, "documentService",
				documentService);
		ReflectionTestUtils.setField(docController, "userService", userService);
		ReflectionTestUtils.setField(docController, "messageSource",
				messageSource);

		reset(documentService);
		reset(messageSource);
	}

	@Test
	public void testGetFile() throws BusinessServiceException, IOException {
		
		User user = new User();
		user.setLogin("login1");
		String texteFile = "texte test";
		S3Object s3Object = new S3Object();
		s3Object.setKey("fichier1");
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(texteFile.length());
		s3Object.setObjectMetadata(metadata);
		InputStream in = new ByteArrayInputStream(texteFile.getBytes("UTF-8"));  
		s3Object.setObjectContent(new S3ObjectInputStream(in, null));
		expect(documentService.loadDocument(1, "login1")).andReturn(s3Object);
		expect(userService.getCurrentUser()).andReturn(user);
		
		replay(documentService);
		replay(userService);
		
		MockHttpServletResponse resp = new MockHttpServletResponse();
		docController.getFile(1, resp);
		assertEquals(10, resp.getContentLength());
		
		verify(documentService);
		verify(userService);
	}
	
	@Test
	public void testAddDocument() {
		TypeDocument typedocument = new TypeDocument();
		typedocument.setIdTypeDocument(1);
		typedocument.setLibelleTypeDocument("libelle1");
		List<TypeDocument> listeTypeDoc = new ArrayList<TypeDocument>();
		listeTypeDoc.add(typedocument);

		expect(documentService.findAllTypeDocument()).andReturn(listeTypeDoc);
		replay(documentService);
		
		ModelMap model = new ModelMap();
		String view = docController.addDocument(model);
		assertEquals(listeTypeDoc, model.get("typeDocuments"));
		assertEquals("addDoc", view);
		verify(documentService);
	}

	@Test
	public void testDeleteDocument() throws BusinessServiceException {
		User user = new User();
		user.setLogin("login1");
		expect(userService.getCurrentUser()).andReturn(user);

		Document document = new Document();
		document.setPk(1);
		document.setNomFichier("fichier document.pdf");
		document.setIntitule("intitule document");
		expect(documentService.getDocument(1, "login1")).andReturn(document);

		documentService.removeDocument(anyObject(Document.class));
		EasyMock.expectLastCall().once();

		IAnswer<String> answer = new IAnswer<String>() {
			@Override
			public String answer() throws Throwable {
				return "messageConfirmation";
			}
		};
		expect(messageSource.getMessage((String) EasyMock.isA(String.class),
				(Object[]) EasyMock.anyObject(), (Locale) EasyMock.isNull()));
		EasyMock.expectLastCall().andAnswer(answer).once();
		EasyMock.replay(messageSource);
		replay(userService);
		replay(documentService);

		RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
		String view = docController.deleteDocument(1, redirectAttributes);
		assertEquals("redirect:/document/listeDocs", view);
		assertEquals(1, redirectAttributes.getFlashAttributes().size());
		assertEquals("messageConfirmation", redirectAttributes
				.getFlashAttributes().get("messageConfirmation"));
		verify(userService);
		verify(documentService);
		verify(messageSource);

	}

	@Test
	public void testNouveauDocument_ErreurDonnees() throws BusinessServiceException {
		IAnswer<String> answer = new IAnswer<String>() {
			@Override
			public String answer() throws Throwable {
				return "messageErreur";
			}
		};
		expect(messageSource.getMessage((String) EasyMock.isA(String.class),
				(Object[]) EasyMock.anyObject(), (Locale) EasyMock.isNull()));
		EasyMock.expectLastCall().andAnswer(answer).once();
		EasyMock.replay(messageSource);

		RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
		DocumentForm documentForm = new DocumentForm();
		ModelAndView mav = docController.nouveauDocument(documentForm,
				redirectAttributes);
		assertEquals("redirect:/document/addDocForm", mav.getViewName());
		assertEquals(1, redirectAttributes.getFlashAttributes().size());
		assertEquals("messageErreur", redirectAttributes.getFlashAttributes()
				.get("messageErreur"));

		verify(messageSource);
	}

	@Test
	public void testNouveauDocument_DonneesOK() throws FileNotFoundException, IOException, BusinessServiceException {
		documentService.addDocument(anyObject(DocumentForm.class));
		EasyMock.expectLastCall().once();

		IAnswer<String> answer = new IAnswer<String>() {
			@Override
			public String answer() throws Throwable {
				return "messageConfirmation";
			}
		};
		expect(messageSource.getMessage((String) EasyMock.isA(String.class),
				(Object[]) EasyMock.anyObject(), (Locale) EasyMock.isNull()));
		EasyMock.expectLastCall().andAnswer(answer).once();
		EasyMock.replay(messageSource);
		replay(documentService);

		RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
		DocumentForm documentForm = new DocumentForm();
		documentForm.setIntituleDocument("intitule");
		documentForm.setIdTypeDocument(1L);
		documentForm.setPeriode("02/2013");
		MockMultipartFile mockFile = new MockMultipartFile("test.txt",
				"Hallo World".getBytes());
		documentForm.setFileData(mockFile);
		
		ModelAndView mav = docController.nouveauDocument(documentForm,
				redirectAttributes);
		assertEquals("redirect:/document/listeDocs", mav.getViewName());
		assertEquals(1, redirectAttributes.getFlashAttributes().size());
		assertEquals("messageConfirmation", redirectAttributes
				.getFlashAttributes().get("messageConfirmation"));
		verify(documentService);
		verify(messageSource);
	}
}