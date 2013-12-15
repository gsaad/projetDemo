package fr.service;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import fr.persistence.dao.DocumentDao;
import fr.persistence.domain.Document;
import fr.persistence.domain.Document;
import fr.persistence.domain.TypeDocument;
import fr.persistence.domain.User;
import fr.service.impl.DocumentServiceImpl;
import fr.web.form.DocumentForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/applicationContext-test.xml" })
public class DocumentServiceImplTest {
		
		private DocumentService documentService ; 
		private DocumentDao documentDao ;
		
		@Before
		public void setUp() throws Exception {	
			documentService = new DocumentServiceImpl();
			documentDao = EasyMock.createMock(DocumentDao.class);
			ReflectionTestUtils.setField(documentService, "documentDao", documentDao);
			reset(documentDao);
		}
		
		@Test
		public void testFindListDocumentByLogin(){
			Document doc = new Document();
			doc.setPk(1);
			doc.setIntitule("passeport");
			List<Document> listDocument = new ArrayList<Document>();
			listDocument.add(doc);
			expect(documentDao.findListDocumentByLogin("login1")).andReturn(listDocument);
			
			replay(documentDao);
			
			List<Document> listDocResult = documentService.findListDocumentByLogin("login1");
			assertEquals(1, listDocResult.size());
			
			verify(documentDao);
		}
		
		
		@Test
		public void testGetDocumentByLogin(){
			Document document = new Document();
			document.setPk(1);
			User user = new User();
			user.setLogin("test");
			document.setUser(user);
			document.setIntitule("intitule1");
			
			expect(documentDao.getDocument(1, "test")).andReturn(document);
			
			replay(documentDao);
			Document documentLogin1 = documentService.getDocument(1, "test");
			assertEquals("intitule1", documentLogin1.getIntitule());
			
			verify(documentDao);
		}
		
		@Test
		public void testUpdateDocument(){
			documentDao.updateDocument(((Document)EasyMock.anyObject()));
			EasyMock.expectLastCall().times(1);
			replay(documentDao);
			documentService.updateDocument(new Document());
			verify(documentDao);
		}
		
		@Test
		@Ignore
		public void testAddDocument() throws FileNotFoundException, IOException{
			documentDao.addDocument((Document)EasyMock.anyObject());
			EasyMock.expectLastCall().times(1);
			DocumentForm documentForm = new DocumentForm();
			documentForm.setIdTypeDocument(1L);
			//FileItem fileItem = 
			//documentForm.setFileData(new CommonsMultipartFile(fileItem));
			documentService.addDocument(documentForm);
		}
		
		@Test
		public void testRemoveDocument(){
			documentDao.removeDocument((Document)EasyMock.anyObject());
			EasyMock.expectLastCall().once();
			replay(documentDao);
			documentService.removeDocument(new Document());
			verify(documentDao);
		}
		
		@Test
		public void testFindAllTypeDocument(){
			TypeDocument typedocument = new TypeDocument();
			typedocument.setIdTypeDocument(1);
			typedocument.setLibelleTypeDocument("libelle1");
			List<TypeDocument> listeTypeDoc = new ArrayList<TypeDocument>();
			listeTypeDoc.add(typedocument);
			
			expect(documentDao.findAllTypeDocument()).andReturn(listeTypeDoc);
			replay(documentDao);
			
			List<TypeDocument> listeTd = documentService.findAllTypeDocument();
			assertEquals(1, listeTd.size());
			verify(documentDao);
		}
}