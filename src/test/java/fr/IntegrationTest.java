package fr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.services.s3.model.S3Object;

import fr.persistence.AbstractUnitTest;
import fr.persistence.domain.Document;
import fr.persistence.domain.User;
import fr.service.BusinessServiceException;
import fr.service.DocumentService;
import fr.service.UserService;
import fr.web.form.DocumentForm;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class IntegrationTest extends AbstractUnitTest {

    @Autowired
    private UserService userService;

    @Autowired
    private DocumentService documentService;
    
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Test
    @Transactional
    public void test_add_load_remove_DocumentFromAmazonBucket() throws BusinessServiceException, IOException {
		authenticatedUserCreated();
		List<Document> listDocument = documentService
				.findListDocumentByLogin(userService.getCurrentUser()
						.getLogin());
		assertEquals(0, listDocument.size());
		
		//add document
		DocumentForm documentForm = new DocumentForm();
		documentForm.setIdTypeDocument(1L);
		MockMultipartFile mockFile = new MockMultipartFile("Hello_world.txt",
				"Hello_world.txt", null, "Hello World".getBytes());
		documentForm.setFileData(mockFile);
		documentForm.setPeriode("02/2013");
		documentForm.setIntituleDocument("document test integration");
		documentForm.setUser(userService.getCurrentUser());
		documentService.addDocument(documentForm);

		List<Document> listDocumentAfter = documentService
				.findListDocumentByLogin(userService.getCurrentUser().getLogin());
		assertEquals(1, listDocumentAfter.size());
		Integer idDocument = listDocumentAfter.get(0).getPk();
		//load document
		S3Object s3Object = documentService.loadDocument(idDocument,
				userService.getCurrentUser().getLogin());
		assertNotNull(s3Object);
		assertEquals("projetdemo", s3Object.getBucketName());
		assertEquals(11, s3Object.getObjectMetadata().getContentLength());
		InputStream inputStream = s3Object.getObjectContent();
		assertEquals("Hello World", IOUtils.toString(inputStream));

		//remove document
		documentService.removeDocument(listDocumentAfter.get(0));

		listDocumentAfter = documentService.findListDocumentByLogin(userService.getCurrentUser().getLogin());
		assertEquals(0, listDocumentAfter.size());
    }

    
    @Test
    @Transactional
    public void testDeleteDocument() {
    	authenticatedUser();
    	List<Document> listDocument = documentService.findListDocumentByLogin(userService.getCurrentUser().getLogin());
    	assertEquals(5, listDocument.size());
    	documentService.removeDocument(listDocument.get(0));
    	
    	List<Document> listDocumentAfter = documentService.findListDocumentByLogin(userService.getCurrentUser().getLogin());
    	assertEquals(4, listDocumentAfter.size());
    }
    
    @Test
    @Transactional
    public void testCreateUser() {
    	assertEquals(5, userService.getNumberOfUsers());
        User user = new User();
        user.setLogin("test_user_int");
        user.setPassword("password");
        user.setVerifyPassword("password");
        user.setFirstName("nom_int");
        user.setLastName("prenom_int");
        user.setEmail("test@test.fr");
        userService.createUser(user);

        User usr = userService.findUser("test_user_int");
        assertNotNull(usr);
        assertEquals("test_user_int", usr.getLogin());
        assertNotNull(usr.getCreationDate());
        assertEquals(6, userService.getNumberOfUsers());
    }
    
    @Test
    @Transactional
    public void testModifyUser() {
    	authenticatedUser();
    	assertEquals(5, userService.getNumberOfUsers());
        User user = userService.getCurrentUser();
        user.setFirstName("first name modif");
        userService.updateUser(user);

        User usr = userService.findUser(userService.getCurrentUser().getLogin());
        assertNotNull(usr);
        assertEquals("test_login1", usr.getLogin());
        assertEquals("first name modif", usr.getFirstName());
        assertEquals("first name modif", userService.getCurrentUser().getFirstName());
    }

    
    @Test
    @Transactional
    public void testAuthenticatedUser() {
        SecurityContextImpl secureContext = new SecurityContextImpl();
        UserDetails userDetails = userDetailsService
                .loadUserByUsername("test_login1");
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                userDetails, "password");
        authenticationManager.authenticate(token);
        secureContext.setAuthentication(token);
        SecurityContextHolder.setContext(secureContext);
        assertNotNull(userService.getCurrentUser());
        assertEquals("test_login1", userService.getCurrentUser().getLogin());
        assertNotNull(userService.getCurrentUser().getLastAccessDate());
    }
    
    /**
     * authentification d'un utilisateur
     */
    private void authenticatedUser() {
        SecurityContextImpl secureContext = new SecurityContextImpl();
        UserDetails userDetails = userDetailsService
                .loadUserByUsername("test_login1");
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                userDetails, "password");
        authenticationManager.authenticate(token);
        secureContext.setAuthentication(token);
        SecurityContextHolder.setContext(secureContext);
    }
    
    
    private void authenticatedUserCreated() {
		User user = new User();
		user.setLogin("test_user_int");
		user.setPassword("password");
		user.setVerifyPassword("password");
		user.setFirstName("nom_int");
		user.setLastName("prenom_int");
		user.setEmail("test@test.fr");
		userService.createUser(user);
        SecurityContextImpl secureContext = new SecurityContextImpl();
        UserDetails userDetails = userDetailsService
                .loadUserByUsername("test_user_int");
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                userDetails, "password");
        authenticationManager.authenticate(token);
        secureContext.setAuthentication(token);
        SecurityContextHolder.setContext(secureContext);
    }
}
